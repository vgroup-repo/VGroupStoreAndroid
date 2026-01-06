package com.vgroup.vgroupstore.data.dao



import androidx.room.*
import com.vgroup.vgroupstore.data.entities.CartEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Query("UPDATE cart_table SET quantity = quantity + 1 WHERE productId = :id")
    suspend fun increaseQty(id: Long)

    @Query("UPDATE cart_table SET quantity = quantity - 1 WHERE productId = :id AND quantity > 1")
    suspend fun decreaseQty(id: Long)

    @Query("DELETE FROM cart_table WHERE productId = :id")
    suspend fun remove(id: Long)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: CartEntity)

    @Query("DELETE FROM cart_table WHERE productId = :id")
    suspend fun delete(id: Long)

    @Query("SELECT * FROM cart_table")
    suspend fun getAll(): List<CartEntity>

    @Query("SELECT * FROM cart_table WHERE productId = :id LIMIT 1")
    suspend fun getById(id: Long): CartEntity?

    @Query("SELECT * FROM cart_table")
    fun getAllFlow(): Flow<List<CartEntity>>

    @Query("DELETE FROM cart_table")
    suspend fun logout()

}
