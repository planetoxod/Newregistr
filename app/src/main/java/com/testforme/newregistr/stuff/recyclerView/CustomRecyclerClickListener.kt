package com.your_teachers.trafficrules.stuff.recyclerView


import android.view.View

interface CustomRecyclerClickListener {

    fun onClick(view: View, position: Int)

    fun onLongClick(view: View, position: Int)

}
