package com.your_teachers.trafficrules.stuff.recyclerView

import android.view.View

interface CustomClickListener {
    fun onClick(view: View)

    fun onLongClick(view: View)
}