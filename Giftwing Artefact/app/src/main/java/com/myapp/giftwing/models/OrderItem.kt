package com.myapp.giftwing.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.myapp.giftwing.utils.getFormattedDate
import kotlinx.serialization.Serializable
import java.util.*

@Entity(tableName = "order_items")
@Serializable
data class OrderItem(
    @PrimaryKey val orderItemId: Int? = null,
    val orderId: String,
    val quantity: Int,
    val createdAt: String = Date().getFormattedDate("yyyy-MM-dd HH:mm"),
    val productId: Int?,
    val userId: Int,
) {
    /** This data will deals with the server from server */
    @Ignore
    var product: Product? = null
    @Ignore
    var order: Order? = null
}
