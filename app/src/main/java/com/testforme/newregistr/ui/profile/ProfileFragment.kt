package com.testforme.newregistr.ui.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.testforme.newregistr.R
import com.testforme.newregistr.databinding.FragmentProfileBinding
import com.testforme.newregistr.databinding.ProfileContentBinding
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.stuff.application.SharedPrefHelper


class ProfileFragment : Fragment(), ToastController, View.OnClickListener,View.OnTouchListener {
    companion object {
        private const val REQUEST_CODE_PERMISSION = 1234
    }

    private lateinit var permissionController: PermissionController
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileModel: ProfileModel
    private var _binding: FragmentProfileBinding? = null
    private lateinit var profileView: ProfileContentBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        permissionController = PermissionController()

        profileModel = ProfileModel()

        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileModel.setViewModel(profileViewModel)
        profileViewModel.setModel(profileModel)

        activity?.let { profileViewModel.setSomethingWent(it.application) }

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        profileView = binding.profileView

        profileViewModel.name.observe(viewLifecycleOwner, {
            profileView.profileCard.nameView.setText(it)
        })

        profileViewModel.email.observe(viewLifecycleOwner, {
            profileView.profileCard.emailView.setText(it)
        })

        profileViewModel.phone.observe(viewLifecycleOwner, {
            profileView.profileCard.phoneView.setText(it)
        })

        profileViewModel.birthday.observe(viewLifecycleOwner, {
            profileView.profileCard.birthdayView.setText(it)
        })

        profileViewModel.dtCreate.observe(viewLifecycleOwner, {
            profileView.profileCard.regView.text = it
        })

        profileViewModel.enabled.observe(viewLifecycleOwner, {
            profileView.profileCard.enabledView.text = it
        })

        profileViewModel.toast.observe(viewLifecycleOwner, {
            context?.let { it1 -> showToast(it1,it,true) }
        })

        profileViewModel.toastText.observe(viewLifecycleOwner, {
            context?.let { it1 -> showToast(it1,it,true) }
        })

        profileViewModel.showProgressDialog.observe(viewLifecycleOwner, {
            actionProgressDialog(it)
        })

        profileView.scrollView.visibility = View.VISIBLE

        profileView.profileCard.btnSave.setOnClickListener(this)
        profileView.btnPhoto.setOnClickListener(this)
        profileView.btnLogout.setOnClickListener(this)
        profileView.profileCard.nameView.setOnClickListener(this)
        profileView.profileCard.emailView.setOnClickListener(this)
        profileView.profileCard.phoneView.setOnClickListener(this)
        profileView.profileCard.birthdayView.setOnClickListener(this)

        profileView.profileCard.nameView.setOnTouchListener(this)
        profileView.profileCard.emailView.setOnTouchListener(this)
        profileView.profileCard.phoneView.setOnTouchListener(this)
        profileView.profileCard.birthdayView.setOnTouchListener(this)

        return root
    }

    private fun actionProgressDialog(boolean: Boolean){
        val waitView = binding.waitforView
        if (boolean) waitView.root.visibility = View.VISIBLE
        else waitView.root.visibility = View.GONE
    }

    override fun showToast(context: Context, text: String, isLong: Boolean) {
        super.showToast(context,text, isLong)
    }

    override fun showToast(context: Context,text: ErrorText, isLong: Boolean) {
        super.showToast(context,text, isLong)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val responseCallback = object : PermissionController.ResponseCallback {
        override fun onResponseCallback() {
            // imagePicker.show(childFragmentManager, null)
            super.onResponseCallback()
        }
    }

    override fun onClick(p0: View?) {
        if (p0 != null) {
            when (p0.id) {
                R.id.btnSave -> {         //update , registration profile
                    with(profileView.profileCard) {
                        with(profileViewModel) {
                            profileViewModel.apply {
                                user = User(
                                    "000", phoneView.text.toString(),
                                    nameView.text.toString(), emailView.text.toString(),
                                    birthdayView.text.toString(), avatar.value.toString(),
                                    "", "", ""
                                )
                                user?.let {
                                    SharedPrefHelper.getInstance().writePreferences(
                                        "user",
                                        Gson().toJson(it, User::class.java)
                                    )
                                }
                                activity?.window?.setSoftInputMode(
                                    WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                                )
                            }
                            startRegister()
                        }
                    }
                }
                R.id.btnPhoto -> {
                    activity?.let {
                        permissionController.ask(
                            it,
                            arrayOf(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA
                            ),
                            responseCallback
                        )
                    }

                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE_PERMISSION -> {
                if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                ) {
                    // permission granted
                    responseCallback.onResponseCallback()
                } else {
                    // permission denied
                }
                return
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (v != null) {
            when (v.id) {
               R.id.name_view,R.id.phone_view,R.id.email_view,R.id.birthday_view->{
                   val view=(v as EditText)
                   if (view.text.toString()==getString(R.string.something_went)){
                       view.text.clear()
                       view.setOnTouchListener(null)
                   }
               }
            }
        }
        return false
    }

}