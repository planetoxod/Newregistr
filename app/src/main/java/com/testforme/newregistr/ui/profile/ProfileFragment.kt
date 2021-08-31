package com.testforme.newregistr.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.testforme.newregistr.databinding.FragmentProfileBinding
import com.testforme.newregistr.databinding.ProfileContentBinding

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var profileModel: ProfileModel
    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        profileModel=ProfileModel()

        profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        profileModel.setViewModel(profileViewModel)

        activity?.let { profileViewModel.setSomethingWent(it.application) }

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val profileView: ProfileContentBinding = binding.profileView
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
            profileView.profileCard.regView.text=it
        })

        profileViewModel.enabled.observe(viewLifecycleOwner, {
            profileView.profileCard.enabledView.text = it
        })

        profileView.scrollView.visibility=View.VISIBLE

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}