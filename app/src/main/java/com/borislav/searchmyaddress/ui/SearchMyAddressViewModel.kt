package com.borislav.searchmyaddress.ui

import com.borislav.searchmyaddress.common.BaseViewModel
//import com.borislav.searchmyaddress.domain.usecase.SearchAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SearchMyAddressViewModel
@Inject
constructor(
    /*private val searchAddressUseCase: SearchAddressUseCase*/
) : BaseViewModel<SearchMyAddressState, SearchMyAddressAction, Unit>(SearchMyAddressState()) {

    override suspend fun handleActions(action: SearchMyAddressAction) {
        when (action) {
//            is SearchMyAddressAction.Search -> {
//                updateState { copy(isLoading = true) }
//
//                try {
//                    val results = searchAddressUseCase(action.query)
//                    val topResults = results.features.filter { it.properties.type == "housenumber" }.take(5)
//
//                    updateState {
//                        copy(
//                            searchResults = topResults.map { it.properties.label },
//                            isLoading = false
//                        )
//                    }
//                } catch (e: Exception) {
//                    updateState {
//                        copy(
//                            error = "Failed to fetch results",
//                            isLoading = false
//                        )
//                    }
//                }
//            }
            is SearchMyAddressAction.SelectAddress -> {
                // Handle the selection of an address if needed.
                // For example, you might navigate to a new screen or update the state with the selected address.
            }
            else -> {
                // Handle other actions if needed.
            }
        }
    }
}
