package com.testforme.newregistr.objects

interface ItemType {

    val itemType: Int

    companion object {
        const val TYPE_USER = 1
        const val TYPE_ZYGOTEUSER = 2
    }

}
