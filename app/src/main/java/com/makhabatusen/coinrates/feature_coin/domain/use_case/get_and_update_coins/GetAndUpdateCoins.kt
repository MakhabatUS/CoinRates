package com.makhabatusen.coinrates.feature_coin.domain.use_case.get_and_update_coins

import androidx.lifecycle.LiveData
import com.makhabatusen.coinrates.feature_coin.data.local_source.entity.CoinEntity
import com.makhabatusen.coinrates.feature_coin.domain.repository.CoinsRepository
import javax.inject.Inject

class GetAndUpdateCoins @Inject constructor(private val repository: CoinsRepository) {

    val coinsFromDB: LiveData<List<CoinEntity>> = repository.getCoinsFromDB()

    suspend operator fun invoke () {
        repository.updateCoinsData()
    }
}


