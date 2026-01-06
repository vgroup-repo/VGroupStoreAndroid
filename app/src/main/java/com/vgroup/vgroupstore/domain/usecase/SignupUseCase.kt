package com.vgroup.toystore.domain.usecase

import com.vgroup.vgroupstore.data.repository.LoginRepositoryImpl
import com.vgroup.vgroupstore.domain.model.SignupResponse
import com.vgroup.vgroupstore.domain.model.UserProfile
import javax.inject.Inject

class SignupUseCase @Inject constructor(
    private val repo: LoginRepositoryImpl
) {

    suspend operator fun invoke(firstName: String,lastName: String, email: String, password: String): SignupResponse {
        return repo.signup(firstName,lastName,email, password)
    }
}
