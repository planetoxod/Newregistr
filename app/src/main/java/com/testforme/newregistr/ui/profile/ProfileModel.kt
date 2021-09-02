package com.testforme.newregistr.ui.profile

class ProfileModel {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var authorizationController: AuthorizationController
    private lateinit var registrationController: RegistrationController

    fun setViewModel(viewModel: ProfileViewModel) {
        profileViewModel = viewModel
        authorizationController= AuthorizationController(profileViewModel)
        registrationController= RegistrationController(profileViewModel)
    }

    fun startRegister() {
        if (profileViewModel.user != null){
            registrationController.register()
        }
    }

 
    private fun authWithPass(){
        if (profileViewModel.user != null){
            authorizationController.authWithPass()
        }
    }

    init {
        authWithPass()
    }
}