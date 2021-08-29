package com.testforme.newregistr.ui.botNavView.Profile.model

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.testforme.newregistr.ui.Profile.stuff.ProfileInterfaces
import com.testforme.newregistr.ui.bottomSheets.BaseBottomSheet

class ProfileModel : ProfileInterfaces.Model {

    override fun authWithPass(manager: FragmentManager) {
        val baseBottomSheet = BaseBottomSheet()
        val bundle = Bundle()
        bundle.putInt(BaseBottomSheet.FragmentTag, BaseBottomSheet.Authorization)
        baseBottomSheet.arguments = bundle
        baseBottomSheet.show(manager, baseBottomSheet.tag)
    }

    override fun openEditBottomSheet(bundle: Bundle, manager: FragmentManager) {
        val baseBottomSheet = BaseBottomSheet()
        bundle.putInt(BaseBottomSheet.FragmentTag, BaseBottomSheet.UpdateUser)
        baseBottomSheet.arguments = bundle
        baseBottomSheet.show(manager, baseBottomSheet.tag)
    }
}