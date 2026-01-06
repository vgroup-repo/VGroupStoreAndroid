package com.vgroup.vgroupstore.presentation.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.vgroup.vgroupstore.R
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels()
    @Inject
    lateinit var prefs: AuthPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Enter email & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(email, password)
        }

        binding.tvSignupRedirect.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToSignupFragment()
            )
        }

        observeState()
    }

    private fun observeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.state.collectLatest { state ->
                when (state) {

                    is LoginUiState.Loading -> {
                        binding.btnLogin.isEnabled = false
                        binding.progressBar.visibility = View.VISIBLE

                    }
                    is LoginUiState.LoginSuccess -> {
                        binding.progressBar.visibility = View.GONE
                        if (state.user != null){
//                            prefs.saveUserObject(state.user!!)
                        }
                        val action = LoginFragmentDirections
                            .actionLoginFragmentToDashboardFragment(state.user)

                        findNavController().navigate(action)

                        // Remove LoginFragment from back stack
                        findNavController().popBackStack(
                            R.id.loginFragment,
                            true
                        )
                    }
                    is LoginUiState.Success -> {

                    }

                    is LoginUiState.Error -> {
                        binding.btnLogin.isEnabled = true
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }

                    LoginUiState.Idle -> {

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
