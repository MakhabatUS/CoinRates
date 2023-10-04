package com.makhabatusen.coinrates.feature_coin.utils

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import com.makhabatusen.coinrates.feature_coin.data.repository.CoinsRepositoryImpl
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class CryptoUpdateWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted  params: WorkerParameters,
    private val repository: CoinsRepositoryImpl
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            repository.updateCoinsData()
            Log.e("worker", "Updated")
            Result.success()
        } catch (e: Exception) {
            Log.e("worker", "Not updated")
            Result.failure()
        }
    }
}
