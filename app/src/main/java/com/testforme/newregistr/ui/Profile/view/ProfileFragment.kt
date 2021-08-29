package com.testforme.newregistr.ui.botNavView.Profile.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.testforme.newregistr.stuff.application.SubApplication
import com.testforme.newregistr.ui.Profile.stuff.ProfileInterfaces
import com.testforme.newregistr.ui.botNavView.Profile.model.ProfileModel
import com.testforme.newregistr.ui.botNavView.Profile.presenter.ProfilePresenter
import com.testforme.newregistr.ui.bottomSheets.UpdateUser.model.UpdateUserModel
import com.testforme.newregistr.ui.bottomSheets.UpdateUser.presenter.UpdateUserPresenter
import com.testforme.newregistr.ui.bottomSheets.UpdateUser.stuff.UpdateUserContract
import com.testforme.newregistr.R
import com.testforme.newregistr.stuff.baseMVP.BaseFragmentImpl
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewType
import com.testforme.newregistr.stuff.observableStuff.UserObserver.UserObserver
import com.testforme.newregistr.stuff.UserHelperImpl
import com.testforme.newregistr.stuff.Util
import kotlinx.coroutines.*
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class ProfileFragment : BaseFragmentImpl(),
        ProfileInterfaces.View,
        UpdateUserContract.View,
        UserObserver {

    private var delayShowProgressDialog: Boolean = false

    //Profile card
    private lateinit var nameView: TextView
    private lateinit var loginView: TextView
    private lateinit var emailView: TextView
    private lateinit var regView: TextView

    private lateinit var mainView: View
    private lateinit var authView: View

    private lateinit var profilePresenter: ProfilePresenter
    private lateinit var updateUserPresenter: UpdateUserPresenter

    private lateinit var userHelper: UserHelperImpl

//    private lateinit var mProgressDialog: SweetAlertDialog
    private lateinit var mIconProgressBar: FloatingActionButton
    private lateinit var mLogoutProgressBar: FloatingActionButton

//    private lateinit var imagePicker: BottomSheetImagePicker.Builder

//    private var permSnackbar: CafeBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.profile_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)

        userHelper = (requireActivity().application as SubApplication).getUserHelper()
        userHelper.add(this)

        userHelper.user?.let {
            changeI(it)
        }

        profilePresenter.viewIsReady(userHelper)
        updateUserPresenter.viewIsReady()
    }

    override fun initView(view: View) {
        nameView = name_view
        loginView = login_view
        emailView = email_view
        regView = reg_view

        mainView = profile_view
        authView = not_auth

        btnLogin.setOnClickListener(mOnClick)
        btnLogout.setOnClickListener(mOnClick)
        btnPhoto.setOnClickListener(mOnClick)

        name.setOnClickListener(mOpenRefactorView)
        login.setOnClickListener(mOpenRefactorView)
        email.setOnClickListener(mOpenRefactorView)
        reg_view.setOnClickListener(mOpenRefactorView)

//        mProgressDialog = SweetAlertDialog(context)
//        mProgressDialog.changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
//        mProgressDialog.titleText = getString(R.string.please_wait)
//        mProgressDialog.setCancelable(false)

        mIconProgressBar = view.findViewById(R.id.btnPhoto)
        mLogoutProgressBar = view.findViewById(R.id.btnLogout)

        imagePicker = BottomSheetImagePicker.Builder(getString(R.string.file_provider))
                .cameraButton(ButtonType.Button)
                .galleryButton(ButtonType.Button)
                .singleSelectTitle(R.string.pick_single)
                .peekHeight(R.dimen.peekHeight)
                .columnSize(R.dimen.columnSize)
                .requestTag("single")

        profilePresenter = ProfilePresenter(this.requireFragmentManager(), ProfileModel())
        updateUserPresenter = UpdateUserPresenter(UpdateUserModel())

        profilePresenter.attachView(this)
        updateUserPresenter.attachView(this)
    }

    private val mOpenRefactorView = View.OnClickListener { view ->
        val bundle = Bundle()
        when (view.id) {
            R.id.name -> {
                bundle.putString("type", "name")
                bundle.putString("bundle", nameView.text.toString())
                profilePresenter.openEditBottomSheet(bundle)
            }
            R.id.login -> {
                bundle.putString("type", "login")
                bundle.putString("bundle", loginView.text.toString())
                profilePresenter.openEditBottomSheet(bundle)
            }
            R.id.email -> {
                bundle.putString("type", "email")
                bundle.putString("bundle", emailView.text.toString())
                profilePresenter.openEditBottomSheet(bundle)
            }
        }
    }

    private val mOnClick = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnLogin -> profilePresenter.auth(userHelper)
            R.id.btnPhoto -> askPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA)

                    .ask(object : PermissionListener {
                        override fun onAccepted(permissionResult: PermissionResult, accepted: List<String>) {
                            //all permissions already granted or just granted
                            //
                            imagePicker.show(childFragmentManager, null)

                        }

                        override fun onDenied(permissionResult: PermissionResult, denied: List<String>, foreverDenied: List<String>) {
                            if (permSnackbar == null) {
                                permSnackbar = CafeBar.builder(context!!)
                                        .duration(CafeBar.Duration.MEDIUM)
                                        .content(R.string.permission_request)
                                        .positiveText(R.string.open_settings)
                                        .positiveColor(android.R.color.white)
                                        .onPositive {
                                            val intent = Intent()
                                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                                            val uri = Uri.fromParts("package", activity!!.packageName, null)
                                            intent.data = uri
                                            startActivity(intent)
                                            it.dismiss()
                                        }
                                        .negativeText(R.string.ok)
                                        .onNegative{ cafeBar -> cafeBar.dismiss() }
                                        .fitSystemWindow()
                                        .floating(true)
                                        .build()
                            }

                            permSnackbar?.show()
                        }
                    })
            R.id.btnLogout -> {
                userHelper.removeUser()
            }
        }
    }

    private fun showToolbar() {
        when (authView.visibility) {
            View.VISIBLE -> (activity as AppCompatActivity).supportActionBar!!.show()
            View.GONE -> if (!this.isHidden)
                (activity as AppCompatActivity).supportActionBar!!.hide()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            showToolbar()
        }
    }

    override fun receiveData(user: User) {
        if (user.imageURL != "") {
            loadUserPhoto(user.imageURL)
        }
        nameView.text = user.name
        loginView.text = user.login
        emailView.text = user.email
        regView.text = user.registerDate
    }

    private fun loadUserPhoto(url: String) {
        showIconProgressBar()

        Glide.get(requireContext()).clearMemory()

        Glide.with(this)
            .load("https://$url")
            .dontAnimate()
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any, target: Target<Drawable>, isFirstResource: Boolean): Boolean {
                    showToast(e?.localizedMessage ?: "UnhandledError", false)

                    cancelIconProgressBar()
                    return false
                }

                override fun onResourceReady(resource: Drawable, model: Any, target: Target<Drawable>, dataSource: DataSource, isFirstResource: Boolean): Boolean {
                    Blurry.with(context)
                        .async()
                        .from((resource as BitmapDrawable).bitmap)
                        .into(backgroundView)

                    hideIconProgressBar()

                    return false
                }
            })
            .preload()

        Glide.with(this)
            .load("https://$url")
            .centerCrop()
            .into(iconView)
    }

    @DelicateCoroutinesApi
    override fun showProgressDialog() {
        delayShowProgressDialog=true
        GlobalScope.launch(Dispatchers.IO) {
            delay(5000)
            if (delayShowProgressDialog && !mProgressDialog.isShowing) {
                mProgressDialog.show()
            }
        }

    }

    override fun hideProgressDialog() {
        delayShowProgressDialog=false
        if (mProgressDialog.isShowing) {
            mProgressDialog.dismiss()
        }
    }


    override fun showIconProgressBar() {
//        mIconProgressBar
    }

    override fun cancelIconProgressBar() {
//        mIconProgressBar.hide()
    }

    override fun hideIconProgressBar() {
//        mIconProgressBar.beginFinalAnimation()
    }

    override fun onRestoreInstanceStateI(savedInstanceState: Bundle) {

    }

    override fun showView(viewType: ViewType) {
        when (viewType) {
            ViewType.AuthView -> {
                authView.visibility = View.VISIBLE
                mainView.visibility = View.GONE

                showToolbar()
            }
            else -> showToast(ErrorText.InvalidViewTypeError)
        }
    }

    override fun hideView(viewType: ViewType) {
        when (viewType) {
            ViewType.AuthView -> {
                authView.visibility = View.GONE
                mainView.visibility = View.VISIBLE

                showToolbar()
            }
            else -> showToast(ErrorText.InvalidViewTypeError)
        }
    }

    override fun dismissI() {

    }

    override fun onImagesSelected(uris: List<Uri>, tag: String?) {
        userHelper.user?.let {user ->
            Util.compressBitmap(requireContext(), iconView.height, iconView.width, uris[0])?.let { bitmap ->
                Util.getFileFromBitmap(requireContext(), bitmap)?.let { file ->
                    updateUserPresenter.uploadImage(user, file)
                }  ?: showToast(ErrorText.FileNotFoundError)
            }  ?: showToast(ErrorText.CompressError)
        } ?: showToast(ErrorText.UserIsNullError)
    }

    override fun onDestroy() {
        userHelper.remove(this)

        profilePresenter.detachView()

        super.onDestroy()
    }

    override fun updateUser(user: User) {
        userHelper.updateUser(user)
        receiveData(user)
    }

    override fun changeI(user: User) {
        profilePresenter.changeI(user)
    }

    override fun updateI(user: User) {
        profilePresenter.updateI(user)
    }

    override fun removeI() {
        profilePresenter.removeI()
    }

}