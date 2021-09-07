package com.testforme.newregistr.ui.profile

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.retrofit.AuthResponse
import com.testforme.newregistr.retrofit.LoginBody
import com.testforme.newregistr.retrofit.RetrofitApi
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PermissionController {
    companion object {
        private const val REQUEST_CODE_PERMISSION = 1234
    }

    interface ResponseCallback{
        fun onResponseCallback(){

        }
    }

    fun ask(activity: Activity,permissions:Array<String>,responseCallback: ResponseCallback) {
        permissions.forEach {
            val permissionStatus =
                ContextCompat.checkSelfPermission(
                    activity.applicationContext,
                    it
                )
            if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
                responseCallback.onResponseCallback()
            } else {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(it),
                    REQUEST_CODE_PERMISSION
                )
            }
        }

    }

}

