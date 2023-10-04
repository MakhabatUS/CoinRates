package com.makhabatusen.coinrates.feature_coin.presentation.coin_price_history

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makhabatusen.coinrates.feature_coin.common.Constants
import com.makhabatusen.coinrates.feature_coin.domain.model.CoinPriceHistory
import com.makhabatusen.coinrates.feature_coin.domain.use_case.get_coin_history.GetCoinHistory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinPriceHistoryViewModel @Inject constructor(
    private val getCoinHistoryUseCase: GetCoinHistory,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _coinPriceHistory = MutableLiveData<List<CoinPriceHistory>>()
    val coinPriceHistory: LiveData<List<CoinPriceHistory>> = _coinPriceHistory

    private var _coinName = MutableLiveData<String>()
    val coinName: LiveData<String> = _coinName

    init {

        viewModelScope.launch {
            savedStateHandle.get<String>(Constants.PARAM_COIN_ID)?.let { coinId ->
                getCoinHistory(coinId)
            }

        }
    }

    private suspend fun getCoinHistory(coinId: String) {

        try {
            val historyData = getCoinHistoryUseCase(coinId)
            _coinPriceHistory.value = historyData
            _coinName.value = coinId


        } catch (e: Exception) {
            Log.e("CoinHistoryViewModel", "Error: ${e.message}")
        }

    }
}

