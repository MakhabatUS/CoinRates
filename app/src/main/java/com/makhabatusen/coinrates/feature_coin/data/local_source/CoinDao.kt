package com.makhabatusen.coinrates.feature_coin.data.local_source
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.makhabatusen.coinrates.feature_coin.data.local_source.entity.CoinEntity


@Dao
interface CoinDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(dataList: List<CoinEntity>)

    @Query("SELECT * FROM coin_data ORDER BY currentPrice DESC" )
    fun getCoins(): LiveData<List<CoinEntity>>
}
