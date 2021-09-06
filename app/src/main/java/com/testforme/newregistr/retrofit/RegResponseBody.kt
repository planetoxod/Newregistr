package com.testforme.newregistr.retrofit

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.testforme.newregistr.objects.ItemType
import kotlinx.parcelize.Parcelize

@Parcelize
data class RegResponseBody(
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
        var avatarURL: String,
        @SerializedName("dt_create")
        var dt_create: String,
        @SerializedName("enabled")
        var enabled: String
): Parcelable, ItemType {

        override val itemType: Int
                get() = ItemType.TYPE_REG_RESPONSE_BODY
}
