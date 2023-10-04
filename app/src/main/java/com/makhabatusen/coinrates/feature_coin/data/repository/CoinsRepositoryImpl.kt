package com.makhabatusen.coinrates.feature_coin.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import com.makhabatusen.coinrates.feature_coin.data.remote_source.CoinGeckoApi
import com.makhabatusen.coinrates.feature_coin.data.local_source.CoinDatabase
import com.makhabatusen.coinrates.feature_coin.data.local_source.entity.CoinEntity
import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.CoinPriceHistoryDto
import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.SupportedCoinItemDto
import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.toCoinEntity
import com.makhabatusen.coinrates.feature_coin.domain.repository.CoinsRepository
import javax.inject.Inject

class CoinsRepositoryImpl @Inject constructor(
    private val api: CoinGeckoApi,
    private val db: CoinDatabase
) : CoinsRepository {

    val coinDataFromDB: LiveData<List<CoinEntity>> = db.coinDataDao().getCoins()

    override fun getCoinsFromDB(): LiveData<List<CoinEntity>> {
        return db.coinDataDao().getCoins()
    }

    override suspend fun updateCoinsData() {
        try {

            val coinsFromApi = api.getCoins()

            val coinEntities = coinsFromApi.map { it.toCoinEntity() }

            db.coinDataDao().insertAll(coinEntities)

        } catch (e: Exception) {
            Log.e("CoinsRepositoryImpl", "Exception" + e.message.toString())
        }
    }


    override suspend fun getCoinPriceHistoryById(coinId: String): CoinPriceHistoryDto {
        return api.getCoinHistory(coinId)
    }

    override suspend fun getSupportedCoins(): List<SupportedCoinItemDto> {
        return api.getSupportedCoinList()
    }

}
