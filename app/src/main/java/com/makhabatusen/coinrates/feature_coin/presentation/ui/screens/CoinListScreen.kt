package com.makhabatusen.coinrates.feature_coin.presentation.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.makhabatusen.coinrates.R
import com.makhabatusen.coinrates.feature_coin.common.Constants
import com.makhabatusen.coinrates.feature_coin.presentation.coin_list.CoinListItem
import com.makhabatusen.coinrates.feature_coin.presentation.coin_list.CoinListViewModel
import com.makhabatusen.coinrates.feature_coin.presentation.supported_coins.SupportedCoinsListItem
import com.makhabatusen.coinrates.feature_coin.presentation.supported_coins.SupportedCoinsViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinListScreen(
    navController: NavController,
    context: Context,
    coinsFromDBVewModel: CoinListViewModel = hiltViewModel(),
    coinsFromApiVewModel: SupportedCoinsViewModel = hiltViewModel()

) {

    val searchText by coinsFromApiVewModel.searchText.collectAsState()
    val coinsFromApi by coinsFromApiVewModel.supportedCoin.collectAsState()
    val isSearching by coinsFromApiVewModel.isSearching.collectAsState()

    Box(modifier = Modifier.fillMaxSize())
    {

        Column(
            modifier = Modifier
                .fillMaxWidth(),

            ) {
            TextField(value = searchText,
                onValueChange = coinsFromApiVewModel::onSearchTextChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text(text = stringResource(R.string.search_coin)) }
            )
            Spacer(modifier = Modifier.height(16.dp))
            if (isSearching) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            } else if (searchText.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(1f / 3f)
                ) {
                    if (coinsFromApi.isNotEmpty()) {
                        items(coinsFromApi) { coin ->
                            SupportedCoinsListItem(coin = coin, onItemClick = {
                                navController.navigate(Screen.CoinPriceHistoryScreen.route + "/${coin.id}")

                            })
                            Divider(color = Color.Cyan)
                        }
                    } else {
                        item {
                            Text(
                                text = Constants.NOT_FOUND,
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


            Divider(color = Color.Blue)

            val coins = coinsFromDBVewModel.coinsData.observeAsState(emptyList())

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(),

                ) {

                coinsFromDBVewModel.updatePricesWorker(context)

                if (coins.value.isNotEmpty()) {
                    items(coins.value) { coin ->

                        CoinListItem(coin = coin, onItemClick = {
                            navController.navigate(Screen.CoinPriceHistoryScreen.route + "/${coin.id}")
                        })
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

}
