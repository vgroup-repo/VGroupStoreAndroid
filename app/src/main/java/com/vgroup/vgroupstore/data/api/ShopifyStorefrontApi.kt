package com.vgroup.vgroupstore.data.api

import com.vgroup.vgroupstore.domain.model.LoginResponse
import com.vgroup.vgroupstore.domain.model.SignupResponse
import com.vgroup.vgroupstore.domain.model.UserResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ShopifyStorefrontApi {

    @POST("graphql.json")
    suspend fun executeQuery(@Body body: GraphQLRequest): LoginResponse

    @POST("graphql.json")
    suspend fun getUserDetails(@Body body: GraphQLRequest): UserResponse
   @POST("graphql.json")
    suspend fun createCustomer(@Body body: GraphQLRequest): SignupResponse



}
