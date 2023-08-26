package com.borislav.searchmyaddress.ui

import android.location.Address

data class SearchMyAddressState(
    val query: String = "",
    val searchResults: List<Address> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
