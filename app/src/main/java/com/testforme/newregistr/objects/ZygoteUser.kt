package com.testforme.newregistr.objects

import com.google.gson.annotations.SerializedName

data class ZygoteUser(
        @SerializedName("phone")
        val phone: String? = null,
        @SerializedName("name")
        val name: String? = null,
        @SerializedName("email")
        val email: String? = null) {
        @SerializedName("birthday")
        var birthday: String? = null
        @SerializedName("avatar_img")
        var avatar_img: String? = null

}