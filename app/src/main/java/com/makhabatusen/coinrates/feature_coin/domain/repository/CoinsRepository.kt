package com.makhabatusen.coinrates.feature_coin.domain.repository

import androidx.lifecycle.LiveData
import com.makhabatusen.coinrates.feature_coin.data.local_source.entity.CoinEntity
import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.CoinPriceHistoryDto
import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.SupportedCoinItemDto

interface CoinsRepository {

    fun getCoinsFromDB(): LiveData<List<CoinEntity>>

    suspend fun updateCoinsData()

    suspend fun getCoinPriceHistoryById(coinId: String): CoinPriceHistoryDto

    suspend fun getSupportedCoins(): List<SupportedCoinItemDto>

}