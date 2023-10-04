package com.makhabatusen.coinrates.feature_coin.presentation.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.makhabatusen.coinrates.R
import com.makhabatusen.coinrates.feature_coin.common.Constants
import com.makhabatusen.coinrates.feature_coin.presentation.coin_price_history.CoinHistoryListItem
import com.makhabatusen.coinrates.feature_coin.presentation.coin_price_history.CoinPriceHistoryViewModel


@Composable
fun CoinPriceHistoryScreen(
    context: Context,
    viewModel: CoinPriceHistoryViewModel = hiltViewModel()
) {

    val coinPriceHistoryList = viewModel.coinPriceHistory.observeAsState(emptyList())
    val coinNameFromVM = viewModel.coinName.observeAsState("")

    val coinName = stringResource(id = R.string.coin_name, coinNameFromVM.value)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    )
    {

        LazyColumn(modifier = Modifier.fillMaxSize()) {

            item {

                Text(
                    text = coinName,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis
                )
                Divider(color = Color.DarkGray)

            }
            if (coinPriceHistoryList.value.isNotEmpty()) {
                items(coinPriceHistoryList.value) { coin ->
                    CoinHistoryListItem(coinPriceHistory = coin)
                    Divider(color = Color.Green)
                }
            } else {

                item {
                    Text(
                        text = Constants.CHECK_INTERNET,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }

}