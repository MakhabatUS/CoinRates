package com.makhabatusen.coinrates.feature_coin.presentation.coin_list

import android.content.Context
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.makhabatusen.coinrates.feature_coin.data.local_source.entity.CoinEntity
import com.makhabatusen.coinrates.feature_coin.domain.use_case.get_and_update_coins.GetAndUpdateCoins
import com.makhabatusen.coinrates.feature_coin.common.Constants
import com.makhabatusen.coinrates.feature_coin.utils.CryptoUpdateWorker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(
    private val getAndUpdateCoins: GetAndUpdateCoins,

    ) : ViewModel() {

    val coinsData: LiveData<List<CoinEntity>> = getAndUpdateCoins.coinsFromDB

    private val handler = Handler(Looper.getMainLooper())

    init {
        viewModelScope.launch {
            getAndUpdateCoins()
        }
        startUpdateTimer()
    }


    private fun startUpdateTimer() {
        handler.postDelayed({
            viewModelScope.launch {
                getAndUpdateCoins()
            }
            startUpdateTimer()
        }, Constants.UPDATE_INTERVAL_MILLIS)
    }

    fun updatePricesWorker(context: Context) {
        val workRequest =
            PeriodicWorkRequestBuilder<CryptoUpdateWorker>(1, TimeUnit.MINUTES).build()
        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            Constants.COIN_UPDATE_WORK,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }
}
