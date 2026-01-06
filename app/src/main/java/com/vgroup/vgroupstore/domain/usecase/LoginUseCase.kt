package com.vgroup.vgroupstore.domain.usecase

import com.vgroup.vgroupstore.data.repository.LoginRepositoryImpl
import com.vgroup.vgroupstore.domain.model.UserProfile
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repo: LoginRepositoryImpl
) {
    suspend operator fun invoke(email: String, password: String): UserProfile {
        return repo.login(email, password)
    }
}
