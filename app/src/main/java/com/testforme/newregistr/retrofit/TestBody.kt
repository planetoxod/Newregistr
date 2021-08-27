package com.your_teachers.trafficrules.retrofit

import com.your_teachers.trafficrules.objects.ItemType
import com.google.gson.annotations.SerializedName

data class TestBody(
        @SerializedName("testCardList")
        val testCardList: List<TestCard>,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("itemId")
        val itemId: Int) : ItemType {

    override val itemType: Int
        get() = ItemType.TYPE_TESTCARD
}