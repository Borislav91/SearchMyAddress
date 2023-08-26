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
import javax.inject.Inject

class SearchMyAddressApiImpl @Inject constructor(
    private val httpClient: HttpClient
) : SearchMyAddressApi {
    override suspend fun fetchAddresses(query: String): Response<List<Address>> {
        return try {
            val apiResponse: ApiResponse = httpClient.get {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api-adresse.data.gouv.fr"
                    path("search/")
                    parameters.append("q", query)
                    parameters.append("type", "housenumber")
                }
            }

            // Directly work with properties and map them to the domain model
            val addresses = apiResponse.features
                .map { it.properties }
                .filter { it.type == "street" && it.housenumber != null }
                .map { it.toDomain() }

            Response.Success(addresses)
        } catch (e: ResponseException) {
            // Handle specific HTTP errors if needed
            Response.Error(e.response.status.description, e.response.status.value)
        } catch (e: Exception) {
            Response.Error(e.localizedMessage, null)
        }
    }
}
