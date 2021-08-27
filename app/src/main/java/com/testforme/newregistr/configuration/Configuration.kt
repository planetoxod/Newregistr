package com.testforme.newregistr.configuration

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.testforme.newregistr.User
import com.testforme.newregistr.objects.*
import com.your_teachers.trafficrules.stuff.application.SharedPrefHelper
import com.your_teachers.trafficrules.stuff.application.SubApplication
import kotlinx.coroutines.*

object Configuration {

    private var packageName=""

    private var latestVersion: MutableLiveData<String> = MutableLiveData()

    private lateinit var parametersConfiguration: Parameters
    var firstLoadingApp = true
    var showChoosingCountry = true

    lateinit var preferences: SharedPreferences

    fun deHtmlTagText(s:String):String {
        var d=""
        val k= setOf('.',',','-',';',' ')
        for (c in s){
           if(c in 'а'..'я' || c in 'А'..'Я' || c in k){
               d+=c
           }
        }
       return d
    }

    fun saveConfiguration(context: Context,application: Application) {
        var user = SharedPrefHelper.getInstance().getUserObject()
        if (user == null) {
            user = User(0, "", "", "", "", "", "")
        }
        val parameters = Parameters(
            user
        )
        if (!parametersConfiguration.compare(parameters)) {
            //  Log.d("TAG_LOG","запись")
            val savingLoadingParameters = SavingLoadingParameters()
            savingLoadingParameters.writeParameters(context, parameters)
            firstLoadingConfiguration(context, application)
        }
    }

    fun firstLoadingConfiguration(context: Context, application: Application) {
        preferences =
            context.getSharedPreferences("file_pref", AppCompatActivity.MODE_PRIVATE)
        packageName = context.packageName

        checkVersion(context)

        val t: Long = 0
        timePutRating = preferences.getLong("timePutRating", t)
        if (timePutRating == t) newTimePutRating()

        timeUpdateApp = preferences.getLong("timeUpdateApp", t)
        if (timeUpdateApp == t) newTimeUpdateApp()

        val savingLoadingParameters = SavingLoadingParameters()
        parametersConfiguration =
            savingLoadingParameters.readParameters(application)

        firstLoadingApp = false

        parametersConfiguration.user.let {
            if (it.token != "") {
                val user = User(it)
                SharedPrefHelper.getInstance()
                    .writePreferences("user", Gson().toJson(user, User::class.java))
                (application as SubApplication).getUserHelper().user = user
            }
        }

        //  savingLoadingParameters.writeParameters(applicationContext, parametersConfiguration)//otkl
    }

    fun askUser(showRateApp: () -> Unit, showUpdateApp: () -> Unit) {
        if (!putRating && isTimePutRating()) {
            showRateApp()
        } else {
            checkAvailabilityUpdate()

            latestVersion.observeForever {
                putUpdating = preferences.getBoolean("putUpdating", false)
                var lastAskVersionName = preferences.getString("lastAskVersionName", "0.0")

                if ((!putUpdating && isTimeUpdateApp()) ||
                                 (lastAskVersionName != latestVersion.value)) {
                    showUpdateApp()

                    lastAskVersionName= latestVersion.value
                    preferences.edit().putString("lastAskVersionName", lastAskVersionName).apply()
                }
            }
        }
    }

    private fun newTimePutRating() {
        timePutRating = (System.currentTimeMillis() + 10 * 24 * 36e5).toLong()
        //  timePutRating = (System.currentTimeMillis()+20000).toLong() //test
        preferences.edit().putLong("timePutRating", timePutRating)
            .apply()
    }

    private fun isTimePutRating(): Boolean {
        return if (System.currentTimeMillis() > timePutRating) {
            newTimePutRating()
            true
        } else false
    }

    private fun newTimeUpdateApp() {
        timeUpdateApp = (System.currentTimeMillis() + 11 * 24 * 36e5).toLong()
        //    timeUpdateApp = (System.currentTimeMillis()+20000).toLong() //test
        preferences.edit().putLong("timeUpdateApp", timeUpdateApp)
            .apply()
    }

    private fun isTimeUpdateApp(): Boolean {
        return if (System.currentTimeMillis() > timeUpdateApp) {
            newTimeUpdateApp()
            true
        } else false
    }

    private fun checkVersion(context: Context){
        try {
            val packageInfo: PackageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val currentVersionName = packageInfo.versionName

            val s="0.0"
            val lastAskVersionName = preferences.getString("lastAskVersionName", s)

            if ((lastAskVersionName == s)) {
                preferences.edit().putString("lastAskVersionName", currentVersionName).apply()
            }
        } catch (e1: PackageManager.NameNotFoundException) {
            e1.printStackTrace()
        }
    }

    private fun checkAvailabilityUpdate() {

        receiverLatestVersion = ReceiverLatestVersion(packageName, latestVersion)
        receiverLatestVersion.start()

        CoroutineScope(Dispatchers.Default).launch {
            var isInitialized=false

            while (!isInitialized){
                delay(100000)//100000

                if (Configuration::receiverLatestVersion.isInitialized) {
                    receiverLatestVersion.interrupt()

                    isInitialized=true
                }
            }
        }
    }

}