package com.your_teachers.trafficrules.retrofit

import com.your_teachers.trafficrules.objects.ItemType
import com.google.gson.annotations.SerializedName

data class TrainingMapsFavouritesBody(
        @SerializedName("testCardList")
        val trainingMapsCardList: List<TrainingMapsCard>,
        @SerializedName("userId")
        val userId: Int,
        @SerializedName("itemId")
        val itemId: Int,
        @SerializedName("infavourites")
        var infavourites: Boolean = false,
        @SerializedName("deleteFavourite")
        var deleteFavourite: Boolean = false) : ItemType {

    override val itemType: Int
        get() = ItemType.TYPE_TRAININGMAPSCARD
}