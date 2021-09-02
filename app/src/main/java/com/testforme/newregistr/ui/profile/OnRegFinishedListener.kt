package com.testforme.newregistr.ui.profile

interface OnRegFinishedListener {

    fun onFinished(authResponse: AuthResponse)

    fun onFinished()

    fun onFailure(t: Throwable)
}
