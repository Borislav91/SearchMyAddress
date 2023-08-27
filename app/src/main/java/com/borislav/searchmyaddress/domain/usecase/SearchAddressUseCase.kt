package com.borislav.searchmyaddress.domain.usecase

import com.borislav.searchmyaddress.core.network.utils.Response
import com.borislav.searchmyaddress.domain.model.Address
import com.borislav.searchmyaddress.domain.repository.SearchMyAddressApi
import timber.log.Timber
import javax.inject.Inject

class SearchAddressUseCase @Inject constructor(
    private val searchMyAddressApi: SearchMyAddressApi
) {
    suspend operator fun invoke(query: String): Response<List<Address>> {
        Timber.tag("SearchAddressUseCase").d("Invoking use case with query: $query")
        return searchMyAddressApi.fetchAddresses(query)
    }
}