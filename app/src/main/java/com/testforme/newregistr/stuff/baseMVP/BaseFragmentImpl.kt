package com.testforme.newregistr.stuff.baseMVP

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.testforme.newregistr.R
import com.testforme.newregistr.objects.ErrorText
import leakcanary.LeakSentry

abstract class BaseFragmentImpl : Fragment(),
        BaseView {

    private var toast: Toast? = null

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater, container, savedInstanceState)
//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        if (savedInstanceState != null)
//            onRestoreInstanceStateI(savedInstanceState)
//    }

   // abstract fun initView(view: View)

    override fun showToast(text: String, isLong: Boolean) {
        if (toast != null)
            toast!!.cancel()
        if (isLong) {
            toast = Toast.makeText(requireActivity().applicationContext, text, Toast.LENGTH_LONG)
            toast!!.show()
        } else {
            toast = Toast.makeText(requireActivity().applicationContext, text, Toast.LENGTH_SHORT)
            toast!!.show()
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
            ErrorText.CompressError -> {
                message = resources.getString(R.string.error_compress_image)
            }
            ErrorText.CardError -> {
                message = "Card Error"
            }
            ErrorText.UnhandledError -> {
                message = resources.getString(R.string.error_unhandled_error)
            }
            else -> message = resources.getString(R.string.error_unhandled_error)
        }

        toast?.cancel()
        toast = if (isLong) {
            Toast.makeText(requireActivity().applicationContext, message, Toast.LENGTH_LONG)
                    .also { it.show() }
        } else {
            Toast.makeText(requireActivity().applicationContext, message, Toast.LENGTH_SHORT)
                    .also { it.show() }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//
//        val refWatcher = LeakSentry.refWatcher
//        refWatcher.watch(this)
//    }
}
