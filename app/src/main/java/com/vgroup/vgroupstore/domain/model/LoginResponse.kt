package com.vgroup.vgroupstore.domain.model



data class LoginResponse(
    val data: LoginData?
) {
    data class LoginData(
        val customerAccessTokenCreate: TokenCreateResult?
    )

    data class TokenCreateResult(
        val customerAccessToken: AccessTokenData?,
        val customerUserErrors: List<CustomerUserError>?
    )

    data class AccessTokenData(
        val accessToken: String?,
        val expiresAt: String?
    )

    data class CustomerUserError(
        val message: String?,
        val field: List<String>?
    )
}

