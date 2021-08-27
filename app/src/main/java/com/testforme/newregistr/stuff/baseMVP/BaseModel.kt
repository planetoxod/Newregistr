package com.your_teachers.trafficrules.stuff.baseMVP

interface BaseModel {

    interface OnFinishedListener {

        fun onFinished()

        fun onFailure(t: Throwable)

    }
}
