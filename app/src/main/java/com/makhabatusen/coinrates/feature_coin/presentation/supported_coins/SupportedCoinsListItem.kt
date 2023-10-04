package com.makhabatusen.coinrates.feature_coin.presentation.supported_coins

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.SupportedCoinItemDto


@Composable
fun SupportedCoinsListItem(
    coin: SupportedCoinItemDto,
    onItemClick: (SupportedCoinItemDto) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onItemClick(coin)
            }
            .padding(20.dp), horizontalArrangement = Arrangement.SpaceBetween) {

        Text(
            text = coin.name,
            style = MaterialTheme.typography.bodyMedium,
            overflow = TextOverflow.Ellipsis
        )

    }
}