package com.testforme.newregistr

import android.os.Parcelable
import com.bumptech.glide.load.resource.bitmap.VideoDecoder.parcel
import com.google.gson.annotations.SerializedName
import com.testforme.newregistr.ui.configuration.Parameters
import com.testforme.newregistr.objects.ItemType
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("username")
    var login: String,
    @SerializedName("imageURL")
    var imageURL: String,
    @SerializedName("email")
    var email: String,
    @SerializedName("registerDate")
    var registerDate: String,
    @SerializedName("token")
    var token: String
): Parcelable, ItemType {

    override val itemType: Int
        get() = ItemType.TYPE_USER

    constructor(userOld: User) : this(
        userOld.id,
        userOld.name,
        userOld.login,
        userOld.imageURL,
        userOld.email,
        userOld.registerDate,
        userOld.token
    )

    fun compare(user: User): Boolean {
        return id == user.id &&
                name == user.name &&
                login == user.login &&
                imageURL == user.imageURL &&
                email == user.email &&
                registerDate == user.registerDate &&
                token == user.token
    }
}