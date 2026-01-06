package com.vgroup.vgroupstore.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_table")
data class CartEntity(
    @PrimaryKey val productId: Long,
    val title: String,
    val price: Double,
    val image: String,
    val quantity: Int
)
