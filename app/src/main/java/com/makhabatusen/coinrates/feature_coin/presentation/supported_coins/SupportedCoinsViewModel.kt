package com.makhabatusen.coinrates.feature_coin.presentation.supported_coins

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.makhabatusen.coinrates.feature_coin.data.remote_source.dto.SupportedCoinItemDto
import com.makhabatusen.coinrates.feature_coin.domain.use_case.get_supported_coins.GetSupportedCoins
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(FlowPreview::class)
@HiltViewModel
class SupportedCoinsViewModel @Inject constructor(
    private val getSupportedCoins: GetSupportedCoins
) : ViewModel() {

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val isSearching = _isSearching.asStateFlow()

    private val _supportedCoins = MutableStateFlow<List<SupportedCoinItemDto>>(emptyList())

    val supportedCoin = searchText
        .debounce(500L)
        .onEach { _isSearching.update {  true} }
        .combine(_supportedCoins) { text, coins ->
            if (text.isBlank()) {
                coins
            } else {
                delay(500L)
                coins. filter {
                    it.doesMatchSearchQuery(text)
                }
            }
        }

        .onEach { _isSearching.update { false } }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(2000),
            _supportedCoins.value
        )


    init {

        viewModelScope.launch {
            getSupportedCoinsList()
        }

    }

    private suspend fun getSupportedCoinsList() {
        val coinsList = getSupportedCoins()
        _supportedCoins.value = coinsList
    }


    fun onSearchTextChange(text: String) {
        _searchText.value = text
    }

}