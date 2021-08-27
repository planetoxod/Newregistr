package com.your_teachers.trafficrules.retrofit

import com.your_teachers.trafficrules.objects.ItemType
import com.google.gson.annotations.SerializedName

data class TicketBody(
        @SerializedName("testCardList")
        val ticketCardList: List<TicketCard>,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("itemId")
        val itemId: Int,
        @SerializedName("infavourites")
        var infavourites: Boolean = false) : ItemType {

    override val itemType: Int
        get() = ItemType.TYPE_TICKETCARD

    constructor(ticketBody: TicketBody) : this (
        ticketBody.ticketCardList,
        ticketBody.userId,
        ticketBody.itemId,
        ticketBody.infavourites
    )
}