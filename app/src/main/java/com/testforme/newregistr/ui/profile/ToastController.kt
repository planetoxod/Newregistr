package com.testforme.newregistr.ui.profile

import android.content.Context
import android.widget.Toast
import com.testforme.newregistr.R
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.stuff.baseMVP.BaseView

interface  ToastController:BaseView  {

    fun showToast(context: Context,text: String, isLong: Boolean) {
        if (isLong) {
            Toast.makeText(context, text, Toast.LENGTH_LONG)
                .also { it.show() }
        } else {
            Toast.makeText(context, text, Toast.LENGTH_SHORT)
                .also { it.show() }
        }
    }

    fun showToast(context: Context, text: ErrorText, isLong: Boolean) {
        var message=""
        when (text) {
            ErrorText.InvalidViewTypeError -> {
                message = context.resources.getString(R.string.error_invalid_viewType)
            }
            ErrorText.LoadingError -> {
                message = context.resources.getString(R.string.error_loading_data)
            }
            ErrorText.UserIsNullError -> {
                message = context.resources.getString(R.string.error_user_is_null)
            }
            ErrorText.FileNotFoundError -> {
                message = context.resources.getString(R.string.error_file_not_found)
            }
            ErrorText.CompressError -> {
                message = context.resources.getString(R.string.error_compress_image)
            }
            ErrorText.CardError -> {
                message = "Card Error"
            }
            ErrorText.UnhandledError -> {
                message = context.resources.getString(R.string.error_unhandled_error)
            }
            else -> {}
        }

        if (isLong) {
            Toast.makeText(context, message, Toast.LENGTH_LONG)
                .also { it.show() }
        } else {
            Toast.makeText(context, message, Toast.LENGTH_SHORT)
                .also { it.show() }
        }
    }
}