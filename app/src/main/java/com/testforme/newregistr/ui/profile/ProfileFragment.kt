package com.testforme.newregistr.ui.profile

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.testforme.newregistr.R
import com.testforme.newregistr.databinding.FragmentProfileBinding
import com.testforme.newregistr.databinding.ProfileContentBinding
import com.testforme.newregistr.objects.ErrorText
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.annotation.NonNull
import com.google.gson.Gson
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.getSystemService







class ProfileFragment : Fragment(), ToastController, View.OnClickListener {
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
            showToast(it)
        })

        profileView.scrollView.visibility = View.VISIBLE

        profileView.profileCard.btnSave.setOnClickListener(this)
        profileView.btnPhoto.setOnClickListener(this)
        profileView.btnLogout.setOnClickListener(this)

        return root
    }

    override fun showToast(text: String, isLong: Boolean) {
    }

    override fun showToast(text: ErrorText, isLong: Boolean) {
    }

    override fun showProgressDialog() {

//        if (!progressDialog.isShowing) {
//                progressDialog.show()
//        }
    }

    override fun hideProgressDialog() {

//        if (progressDialog.isShowing) {
//            progressDialog.dismiss()
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val responseCallback = object :PermissionController.ResponseCallback {
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

}