package com.makhabatusen.coinrates.feature_coin.data.local_source.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "coin_data")
data class CoinEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val currentPrice: Double?,
    val lastUpdated: String,
    val image: String?,
)





