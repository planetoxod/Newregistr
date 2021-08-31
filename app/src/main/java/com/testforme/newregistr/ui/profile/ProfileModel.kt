package com.testforme.newregistr.ui.profile

class ProfileModel {
    private lateinit var profileViewModel: ProfileViewModel

    fun setViewModel(viewModel: ProfileViewModel){
        profileViewModel=viewModel
    }
}