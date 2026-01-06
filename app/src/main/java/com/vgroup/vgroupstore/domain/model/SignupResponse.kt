package com.vgroup.vgroupstore.domain.model

data class SignupResponse(
    val data: SignupData?
)

data class SignupData(
    val customerCreate: CustomerCreateResult?
)

data class CustomerCreateResult(
    val customer: Customer?,
    val customerUserErrors: List<CustomerUserError>?
)


data class CustomerUserError(
    val field: List<String>?,
    val message: String?
)

