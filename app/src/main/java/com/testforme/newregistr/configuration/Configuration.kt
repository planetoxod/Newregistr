package com.testforme.newregistr.configuration

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.testforme.newregistr.objects.User
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
            user = User("0", "", "", "", "", "", "",false,"")
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


//        val t: Long = 0
//        timePutRating = preferences.getLong("timePutRating", t)
//        if (timePutRating == t) newTimePutRating()
//
//        timeUpdateApp = preferences.getLong("timeUpdateApp", t)
//        if (timeUpdateApp == t) newTimeUpdateApp()

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


}