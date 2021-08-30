package com.testforme.newregistr.objects

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    var id: String,
    @SerializedName("phone")
    var phone: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("avatar")
    var avatar: String,
    @SerializedName("dt_create")
    var dt_create: String,
    @SerializedName("enabled")
    var enabled: String,
    @SerializedName("token")
    var token: String
): Parcelable, ItemType {

    constructor(userOld: User) : this(
        userOld.id,
        userOld.phone,
        userOld.name,
        userOld.email,
        userOld.birthday,
        userOld.avatar,
        userOld.dt_create,
        userOld.enabled,
        userOld.token
    )

    override val itemType: Int
        get() = ItemType.TYPE_USER
}