package com.borislav.searchmyaddress.ui

import com.borislav.searchmyaddress.domain.model.Address


sealed class SearchMyAddressAction {
    data class Search(val query: String) : SearchMyAddressAction()
    data class SelectAddress(val address: Address) : SearchMyAddressAction()
}