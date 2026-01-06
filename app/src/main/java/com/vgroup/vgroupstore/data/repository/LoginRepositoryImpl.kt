package com.vgroup.vgroupstore.data.repository

import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.core.Config
import com.vgroup.vgroupstore.data.api.GraphQLRequest
import com.vgroup.vgroupstore.data.api.ShopifyStorefrontApi
import com.vgroup.vgroupstore.domain.model.SignupResponse
import com.vgroup.vgroupstore.domain.model.UserProfile
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val api: ShopifyStorefrontApi,
    private val prefs: AuthPrefs
) {


    suspend fun signup(firstName: String, lastName: String, email: String, password: String): SignupResponse {

        val query = Config.CUSTOMER_CREATE_QUERY
        val variables = mapOf(
            "input" to mapOf(
                "email" to email,
                "firstName" to firstName,
                "lastName" to lastName,
                "password" to password
            )
        )

        val body = GraphQLRequest(query, variables)

        val response = api.createCustomer(body)   // returns SignupResponse

        return response  // <-- SUCCESS
    }


    suspend fun login(email: String, password: String): UserProfile {

        // Build login body
        val loginBody = GraphQLRequest(
            query = Config.LOGIN_QUERY,
            variables = mapOf(
                "input" to mapOf(
                    "email" to email,
                    "password" to password
                )
            )
        )

        // Execute login mutation
        val loginResponse = api.executeQuery(loginBody)

        val token = loginResponse.data
            ?.customerAccessTokenCreate
            ?.customerAccessToken
            ?.accessToken

        // Login failed → throw error
        if (token == null) {
            val error = loginResponse.data
                ?.customerAccessTokenCreate
                ?.customerUserErrors
                ?.firstOrNull()
                ?.message ?: "Login failed"

            throw Exception(error)
        }

        // Save login session
        prefs.saveUser(email, token)
        // Fetch USER DETAILS
        return  getUserDetails(token)

    }

    suspend private fun getUserDetails(token: String) : UserProfile {
        val userBody = GraphQLRequest(
            query = Config.USER_QUERY,
            variables = mapOf("token" to token)
        )

        val userResponse = api.getUserDetails(userBody)

        val customer = userResponse.data?.customer
            ?: throw Exception("Unable to fetch user details")

        prefs.saveUserObject(customer.toUserProfile())

        return customer.toUserProfile()
    }

    suspend fun fetchUserFromToken(token: String): UserProfile {
        return getUserDetails(token)
    }

}
