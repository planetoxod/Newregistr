package com.testforme.newregistr.configuration

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ItemType
import com.your_teachers.trafficrules.objects.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class Parameters(
    @SerializedName("user")
    val user: User
    ) : Parcelable, ItemType {

    fun compare(parameters:Parameters):Boolean{
        return user.compare(parameters.user)
    }

    override val itemType: Int
        get() = ItemType.TYPE_PARAMETERSCONFIGURATION
}
