package com.testforme.newregistr.stuff.application

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson
import com.testforme.newregistr.objects.User

class SharedPrefHelper {
    companion object {
        private var mInstance: SharedPrefHelper? = null

        fun getInstance() : SharedPrefHelper {
            if (mInstance == null)
                mInstance = SharedPrefHelper()
            return mInstance!!
        }
    }

    private var myPreferences: SharedPreferences? = null

    fun init(applicationContext: Context) {
        myPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    fun writePreferences(key: String, value: String) {
        myPreferences?.let { myPreferences ->
            val e = myPreferences.edit()
            e.putString(key, value)
            e.apply()
        }
    }

    fun getPrefString(key: String, defValue: String): String? {
        return myPreferences?.getString(key, defValue)
    }

    fun getUserObject(): User? {
        val user = getPrefUser()
        return Gson().fromJson(user, User::class.java)
    }

    private fun getPrefUser(): String {
        return myPreferences?.getString("user", "") ?: ""
    }
}