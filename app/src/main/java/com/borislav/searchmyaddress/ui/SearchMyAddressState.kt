package com.borislav.searchmyaddress.ui

import com.borislav.searchmyaddress.domain.model.Address


data class SearchMyAddressState(
    val query: String = "",
    val searchResults: List<Address> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedAddress: Address? = null
)
