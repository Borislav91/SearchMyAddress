package com.borislav.searchmyaddress.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FeatureResponse(
    val type: String,
    val properties: PropertiesResponse
)
