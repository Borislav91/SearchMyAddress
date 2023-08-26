package com.borislav.searchmyaddress.ui

import androidx.lifecycle.viewModelScope
import com.borislav.searchmyaddress.common.BaseViewModel
import com.borislav.searchmyaddress.core.network.utils.Response
import com.borislav.searchmyaddress.domain.usecase.SearchAddressUseCase
//import com.borislav.searchmyaddress.domain.usecase.SearchAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SearchMyAddressViewModel
@Inject
constructor(
    private val searchAddressUseCase: SearchAddressUseCase
) : BaseViewModel<SearchMyAddressState, SearchMyAddressAction, Unit>(SearchMyAddressState()) {


    init {
        searchAddresses("1, route de la Paix, 75001 Paris")
    }

    override suspend fun handleActions(action: SearchMyAddressAction) {
        when (action) {
            is SearchMyAddressAction.Search -> {
                updateState { copy(isLoading = true) }

            }
            is SearchMyAddressAction.SelectAddress -> {
                // Handle the selection of an address if needed.
                // For example, you might navigate to a new screen or update the state with the selected address.
            }
            else -> {
                // Handle other actions if needed.
            }
        }
    }

    private fun searchAddresses(query: String) {
        Timber.tag("SearchMyAddressViewModel").d("Searching addresses for query: $query")


        viewModelScope.launch {
            updateState { copy(isLoading = true) }

            when (val response = searchAddressUseCase(query)) {
                is Response.Success -> {
                    Timber.tag("SearchMyAddressViewModel").d("Successfully fetched ${response.data.size} addresses.")
                    updateState {
                        copy(
                            searchResults = response.data,
                            isLoading = false
                        )
                    }
                }
                is Response.Error -> {
                    Timber.tag("SearchMyAddressViewModel").d("Error fetching addresses: ${response.error}")
                    updateState {
                        copy(
                            error = response.error,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

}
