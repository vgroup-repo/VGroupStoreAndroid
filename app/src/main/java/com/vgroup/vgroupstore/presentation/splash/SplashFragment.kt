package com.vgroup.vgroupstore.presentation.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.vgroup.vgroupstore.R
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.data.repository.LoginRepositoryImpl
import com.vgroup.vgroupstore.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    @Inject lateinit var loginRepository: LoginRepositoryImpl
    @Inject
    lateinit var prefs: AuthPrefs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            kotlinx.coroutines.delay(3000)
            if (!isAdded) return@launch // prevent crash if user leaves early

            if (prefs.isLoggedIn()) {

                try {
                    val user = loginRepository.fetchUserFromToken(prefs.getToken()!!)

//                     Navigate to dashboard
                    val action = SplashFragmentDirections
                        .actionSplashFragmentToDashboardFragment(user)

                    findNavController().navigate(
                        action,
                        NavOptions.Builder()
                            .setPopUpTo(R.id.splashFragment, true)
                            .build()
                    )

                } catch (e: Exception) {
                    e.printStackTrace()

                    prefs.logout()

                    navigateToLogin()
                }
//                SplashFragmentDirections.actionSplashFragmentToDashboardFragment(state.user)

            }else{
                navigateToLogin()
            }

        }
    }

    private fun navigateToLogin() {

        findNavController().navigate(
            SplashFragmentDirections.actionSplashFragmentToLoginFragment(),
            NavOptions.Builder()
                .setPopUpTo(R.id.splashFragment, true)
                .build()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
