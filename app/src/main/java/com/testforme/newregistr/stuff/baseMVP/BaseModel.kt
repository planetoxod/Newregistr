package com.testforme.newregistr.stuff.baseMVP

interface BaseModel {

    interface OnFinishedListener {

        fun onFinished()

        fun onFailure(t: Throwable)

    }
}
