package com.your_teachers.trafficrules.stuff.application

import android.app.Application
import com.your_teachers.trafficrules.stuff.UserHelperImpl

class SubApplication : Application() {

    private lateinit var userHelper: UserHelperImpl

    override fun onCreate() {
        super.onCreate()

        userHelper = UserHelperImpl()

        SharedPrefHelper.getInstance().init(applicationContext)

    }

    fun getUserHelper(): UserHelperImpl {
        return userHelper
    }
}