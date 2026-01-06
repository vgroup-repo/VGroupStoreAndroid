package com.vgroup.vgroupstore.presentation.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vgroup.vgroupstore.databinding.FragmentSignupBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignupViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnSignup.setOnClickListener {
            val firstName = binding.etFirstName.text.toString().trim()
            val lastName = binding.etLastName.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirm = binding.etConfirmPassword.text.toString().trim()

            if (firstName.isEmpty() ||lastName.isEmpty() || email.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirm) {
                Toast.makeText(requireContext(), "Passwords do not match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.signup(firstName ,lastName, email, password)
        }

        binding.tvLoginRedirect.setOnClickListener {
            findNavController().navigate(
                SignupFragmentDirections.actionSignupFragmentToLoginFragment()
            )
        }

        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {

                    is SignupUiState.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                        binding.btnSignup.isEnabled = false
                    }

                    is SignupUiState.Success -> {

                    }
                    is SignupUiState.CustomerCreateSuccess -> {
                        binding.btnSignup.isEnabled = true

                        Toast.makeText(requireContext(), "Signup successful, Login to continue.", Toast.LENGTH_SHORT)
                            .show()

                        findNavController().navigate(
                            SignupFragmentDirections.actionSignupFragmentToLoginFragment()
                        )
                    }

                    is SignupUiState.Error -> {
                        binding.btnSignup.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }

                    SignupUiState.Idle -> {}
                    is SignupUiState.LoginSuccess -> {
                        val action = SignupFragmentDirections
                            .actionSignupFragmentToDashboardFragment(state.user)

                        findNavController().navigate(action)
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
