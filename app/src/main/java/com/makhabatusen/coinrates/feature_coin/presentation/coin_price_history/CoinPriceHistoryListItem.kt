package com.makhabatusen.coinrates.feature_coin.presentation.coin_price_history

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import com.makhabatusen.coinrates.R
import com.makhabatusen.coinrates.feature_coin.domain.model.CoinPriceHistory
import com.makhabatusen.coinrates.feature_coin.common.Constants
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CoinHistoryListItem(
    coinPriceHistory: CoinPriceHistory,
    modifier: Modifier = Modifier
) {
    val priceFormatted = remember {
        String.format(Constants.DOUBLE_VALUE_FORMAT, coinPriceHistory.price)
    }

    val dateFormatted = remember {
        val date = Date(coinPriceHistory.date)
        val dateFormatter = SimpleDateFormat(Constants.DATE_FORMAT, Locale.GERMANY)
        dateFormatter.format(date)
    }

    val date = stringResource(id = R.string.date, dateFormatted)
    val price = stringResource(id = R.string.euro_sign, priceFormatted)



    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center

    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = price,
            style = MaterialTheme.typography.bodyMedium,
            fontStyle = FontStyle.Italic
        )
    }
}