package com.makhabatusen.coinrates.feature_coin.domain.use_case.get_supported_coins

import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.SupportedCoinItemDto
import com.makhabatusen.coinrates.feature_coin.domain.repository.CoinsRepository
import javax.inject.Inject

class GetSupportedCoins @Inject constructor(
    private val repository: CoinsRepository

) {

    suspend operator fun invoke(): List<SupportedCoinItemDto> {
        return repository.getSupportedCoins()
    }
}