package com.borislav.searchmyaddress.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PropertiesResponse(
    val type: String,
    val housenumber: String?,
    val street: String,
    val postcode: String,
    val city: String
)
