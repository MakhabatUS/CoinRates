package com.makhabatusen.coinrates.feature_coin.data.remote_source.dto

import com.google.gson.annotations.SerializedName

data class SupportedCoinItemDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String
) {

    fun doesMatchSearchQuery(query : String) : Boolean {
        val matchingCombinations = listOf(
            id,
            name,
            "${id.first()}",
            "${name.first()}",
        )

        return matchingCombinations.any{
            it.contains(query, ignoreCase = true)
        }

    }
}