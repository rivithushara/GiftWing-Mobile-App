package com.myapp.giftwing.models

import androidx.room.Embedded
import androidx.room.Relation

data class OrderPaymentDetails(
    @Embedded
    val orderPayment: OrderPayment,
    @Relation(
        parentColumn = "providerId",
        entityColumn = "providerId",
        entity = UserPaymentProvider::class,
    )
    val userPaymentProviderDetails: UserPaymentProviderDetails,
)
