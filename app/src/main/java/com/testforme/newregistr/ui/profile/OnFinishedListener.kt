package com.testforme.newregistr.ui.profile

interface OnFinishedListener {

    fun onFinished(authResponse: AuthResponse)

    fun onFinished()

    fun onFailure(t: Throwable)
}