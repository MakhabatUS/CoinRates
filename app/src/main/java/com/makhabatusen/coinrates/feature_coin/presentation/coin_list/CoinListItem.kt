package com.makhabatusen.coinrates.feature_coin.presentation.coin_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.makhabatusen.coinrates.R
import com.makhabatusen.coinrates.feature_coin.data.local_source.entity.CoinEntity
import com.makhabatusen.coinrates.feature_coin.common.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CoinListItem(
    coin: CoinEntity,
    onItemClick: (CoinEntity) -> Unit
) {

    val lastUpdatedFormatted = remember {
        val dateFormatter = SimpleDateFormat(Constants.DATE_FORMAT_LAST_UPDATED_PRICE, Locale.GERMANY)
        val date = SimpleDateFormat(Constants.DATE_FORMAT_INITIAL, Locale.GERMANY).parse(coin.lastUpdated)
        dateFormatter.format(date ?: Date())
    }

    val coinName = stringResource(id = R.string.coin_name, coin.name)
    val currentPrice = stringResource(id = R.string.euro_sign, coin.currentPrice.toString())
    val lastUpdated = stringResource(id = R.string.last_updated, lastUpdatedFormatted)


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(coin)
            }
            .padding(20.dp),
        horizontalArrangement = Arrangement.Center) {

        Text(
            text = "$coinName\n $currentPrice\n $lastUpdated",
            style = MaterialTheme.typography.bodyLarge,
        )

    }
}