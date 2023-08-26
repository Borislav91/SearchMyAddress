package com.borislav.searchmyaddress.data.repository

import com.borislav.searchmyaddress.core.network.utils.Response
import com.borislav.searchmyaddress.data.model.ApiResponse
import com.borislav.searchmyaddress.data.model.mapper.toDomain
import com.borislav.searchmyaddress.domain.model.Address
import com.borislav.searchmyaddress.domain.repository.SearchMyAddressApi
import io.ktor.client.HttpClient
import io.ktor.client.features.ResponseException
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import timber.log.Timber
import javax.inject.Inject

class SearchMyAddressApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : SearchMyAddressApi {
    override suspend fun fetchAddresses(query: String): Response<List<Address>> {
        return try {
            Timber.tag("SearchMyAddressApiImpl").d("Fetching addresses for query: $query")
            val apiResponse: ApiResponse = httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api-adresse.data.gouv.fr"
                    path("search/")
                    parameters.append("q", query)
                    parameters.append("type", "housenumber")
                }
            }
            Timber.tag("SearchMyAddressApiImpl").d("Received API response: $apiResponse")  // Log the raw response

            // Directly work with properties and map them to the domain model
            val addresses = apiResponse.features
                .map { it.properties }
                .filter { it.type == "street" && it.housenumber != null }
                .map { it.toDomain() }

            Timber.tag("SearchMyAddressApiImpl").d("Mapped addresses: $addresses")  // Log the processed addresses


            Response.Success(addresses)
        } catch (e: ResponseException) {
            Timber.tag("SearchMyAddressApiImpl").e(e, "HTTP error while fetching addresses")  // Log the error with exception
            // Handle specific HTTP errors if needed
            Response.Error(e.response.status.description, e.response.status.value)
        } catch (e: Exception) {
            Timber.tag("SearchMyAddressApiImpl").e(e, "General error while fetching addresses")  // Log the error with exception
            Response.Error(e.localizedMessage, null)
        }
    }
}
