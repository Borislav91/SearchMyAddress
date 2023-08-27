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

            // Determine if the query starts with a number.
            val isHouseNumberQuery = query.matches(Regex("^\\d.*"))

            val apiResponse: ApiResponse = httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api-adresse.data.gouv.fr"
                    path("search/")
                    parameters.append("q", query)

                    if(isHouseNumberQuery) {
                        parameters.append("type", "housenumber")
                    }
                }
            }
            Timber.tag("SearchMyAddressApiImpl").d("Number of features: ${apiResponse.features.size}")


            Timber.tag("SearchMyAddressApiImpl").d("Received API response: $apiResponse")  // Log the raw response

            // Directly work with properties and map them to the domain model
            val addresses = apiResponse.features
                .filter {
                    if (isHouseNumberQuery) {
                        it.type == "housenumber"
                    } else {
                        it.type == "street"
                    }
                }
                .take(5)
                .map { it.properties.toDomain() }



//            val filteredResults = apiResponse.features.filter {
//                if (isHouseNumberQuery) {
//                    it.properties.type == "housenumber"
//                } else {
//                    it.properties.type == "street"
//                }
//            }
//            Timber.tag("SearchMyAddressApiImpl").d("Number of filtered results: ${filteredResults.size}")
//
//            if(filteredResults.isNotEmpty()) {
//                val mappedResults = filteredResults.take(5).map { it.properties.toDomain() }
//                Timber.tag("SearchMyAddressApiImpl").d("Mapped addresses: $mappedResults")
//                Response.Success(mappedResults)
//            } else {
//                Timber.tag("SearchMyAddressApiImpl").d("No results to map.")
//                Response.Success(emptyList())
//            }

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
