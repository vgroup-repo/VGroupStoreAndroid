package com.vgroup.vgroupstore.domain.model
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class UserResponse(
    val data: UserData?
)

data class UserData(
    val customer: Customer?
)

data class Customer(
    val id: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val phone: String?,
    val orders: Orders?
) {
    fun toUserProfile() = UserProfile(
        id = id.orEmpty(),
        firstName = firstName.orEmpty(),
        lastName = lastName.orEmpty(),
        email = email.orEmpty(),
        phone = phone
    )
}

data class Orders(
    val edges: List<OrderEdge>?
)

data class OrderEdge(
    val node: OrderNode?
)

data class OrderNode(
    val orderNumber: Int?,
    val processedAt: String?,
    val totalPriceV2: Money?
)

data class Money(
    val amount: String?,
    val currencyCode: String?
)

@Parcelize
data class UserProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String?
) : Parcelable

