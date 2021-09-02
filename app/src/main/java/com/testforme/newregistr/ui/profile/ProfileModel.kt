package com.testforme.newregistr.ui .profile

class ProfileModel {
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var authorizationController: AuthorizationController
    
    fun setViewModel(viewModel: ProfileViewModel) {
        profileViewModel = viewModel
        authorizationController=AuthorizationController(profileViewModel)
    }

 
    fun authWithPass(){
        if (profileViewModel.user != null){
            authorizationController.authWithPass()
        }
    }

    init {
        authWithPass()
    }
}