package com.borislav.searchmyaddress.ui

import androidx.lifecycle.viewModelScope
import com.borislav.searchmyaddress.common.BaseViewModel
import com.borislav.searchmyaddress.core.network.utils.Response
import com.borislav.searchmyaddress.domain.model.Address
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

    override suspend fun handleActions(action: SearchMyAddressAction) {
        when (action) {
            is SearchMyAddressAction.Search -> {
                val trimmedQuery = action.query.trim()

                if (isValidQuery(trimmedQuery)) {
                    searchAddresses(trimmedQuery)
                } else {
                    // TODO Notify the user about the invalid input or silently return
                }
            }
            is SearchMyAddressAction.SelectAddress -> {
                Timber.tag("SearchMyAddressViewModel").d("Address selected: ${action.address.streetName}")

                updateState {
                    copy(
                        selectedAddress = action.address,
                        isAddressSelected = true
                    )
                }
            }
            is SearchMyAddressAction.ResetSelectedAddress -> {
                Timber.tag("SearchMyAddressViewModel").d("Resetting selected address")

                updateState {
                    copy(
                        selectedAddress = null,
                        isAddressSelected = false
                    )
                }
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
    private fun isValidQuery(query: String): Boolean {
        return query.length in 3..200 && query[0].isLetterOrDigit()
    }
}
