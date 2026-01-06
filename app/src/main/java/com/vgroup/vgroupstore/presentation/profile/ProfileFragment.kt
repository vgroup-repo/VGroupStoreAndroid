package com.vgroup.vgroupstore.presentation.profile




import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.databinding.FragmentProfileBinding
import com.vgroup.vgroupstore.domain.model.UserProfile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var prefs: AuthPrefs
    lateinit var   user : UserProfile

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         user = prefs.getUserObject()!!


        observeUiState()

        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            Toast.makeText(requireContext(), "Logged out", Toast.LENGTH_SHORT).show()

        }

        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Edit profile coming soon", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeUiState() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collectLatest { state ->

                when (state) {

                    is ProfileUiState.Loaded -> {
                        binding.tvProfileName.text = state.name
                        binding.tvProfileEmail.text = state.email
                    }

                    ProfileUiState.LoggedOut -> {
                        val action = ProfileFragmentDirections
                            .actionProfileFragmentToLoginFragment()
                        findNavController().navigate(action)
                    }

                    ProfileUiState.Loading -> {

                    }

                    is ProfileUiState.Error -> {
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
