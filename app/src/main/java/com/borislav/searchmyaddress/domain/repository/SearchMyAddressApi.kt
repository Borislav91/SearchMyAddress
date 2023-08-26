package com.borislav.searchmyaddress.domain.repository

import com.borislav.searchmyaddress.core.network.utils.Response
import com.borislav.searchmyaddress.domain.model.Address


interface SearchMyAddressApi {
    suspend fun fetchAddresses(query: String): Response<List<Address>>
}
