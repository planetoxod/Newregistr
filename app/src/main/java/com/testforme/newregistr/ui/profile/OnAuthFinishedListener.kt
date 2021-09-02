package com.testforme.newregistr.ui.profile

import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.retrofit.ResponseBody

interface OnAuthFinishedListener  {

    fun onFinished(responseList: List<ResponseBody>)

    fun onFinished(zygoteUser: ZygoteUser)

}