package com.your_teachers.trafficrules.retrofit

import com.your_teachers.trafficrules.objects.ItemType
import com.google.gson.annotations.SerializedName

data class ExamBody(
    @SerializedName("testCardList")
        val ticketCardList: List<ExamCard>,
    @SerializedName("userId")
        val userId: Int,
    @SerializedName("itemId")
        val itemId: Int) : ItemType {

    override val itemType: Int
        get() = ItemType.TYPE_EXAMCARD

    constructor(examBody: ExamBody) : this (
        examBody.ticketCardList,
        examBody.userId,
        examBody.itemId,
    )
}