package com.borislav.searchmyaddress.ui

import android.location.Address

sealed class SearchMyAddressAction {
    data class Search(val query: String) : SearchMyAddressAction()
    data class SelectAddress(val address: Address) : SearchMyAddressAction()
}