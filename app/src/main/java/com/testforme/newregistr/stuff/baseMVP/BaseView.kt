package com.testforme.newregistr.stuff.baseMVP

import android.os.Bundle
import com.testforme.newregistr.objects.ErrorText

interface BaseView {

    fun showToast(text: String, isLong: Boolean = false)

    fun showToast(text: ErrorText, isLong: Boolean = false)

}
