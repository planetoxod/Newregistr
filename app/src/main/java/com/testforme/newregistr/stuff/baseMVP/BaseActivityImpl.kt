package com.your_teachers.trafficrules.stuff.baseMVP

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.your_teachers.trafficrules.R
import com.your_teachers.trafficrules.objects.ErrorText
import leakcanary.LeakSentry

abstract class BaseActivityImpl : AppCompatActivity(), BaseView {

    private var toast: Toast? = null

    override fun showToast(text: String, isLong: Boolean) {
        toast?.cancel()
        toast = if (isLong) {
            Toast.makeText(applicationContext, text, Toast.LENGTH_LONG)
                    .also { it.show() }
        } else {
            Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT)
                    .also { it.show() }
        }
    }

    override fun showToast(text: ErrorText, isLong: Boolean) {
        val message: String
        when (text) {
            ErrorText.InvalidViewTypeError -> {
                message = resources.getString(R.string.error_invalid_viewType)
            }
            ErrorText.LoadingError -> {
                message = resources.getString(R.string.error_loading_data)
            }
            ErrorText.UserIsNullError -> {
                message = resources.getString(R.string.error_user_is_null)
            }
            ErrorText.FileNotFoundError -> {
                message = resources.getString(R.string.error_file_not_found)
            }
            ErrorText.CompressError -> {
                message = resources.getString(R.string.error_compress_image)
            }
            ErrorText.CardError -> {
                message = "Card Error"
            }
            ErrorText.UnhandledError -> {
                message = resources.getString(R.string.error_unhandled_error)
            }
        }

        toast?.cancel()
        toast = if (isLong) {
            Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
                    .also { it.show() }
        } else {
            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT)
                    .also { it.show() }
        }
    }

    public override fun onDestroy() {
        super.onDestroy()

        val refWatcher = LeakSentry.refWatcher
        refWatcher.watch(this)
    }
}