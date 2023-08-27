package com.borislav.searchmyaddress.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertiesResponse(
    val type: String,
    val housenumber: String? = null,
    val street: String? = null,
    val postcode: String,
    val city: String
)
