package com.borislav.searchmyaddress.data.model

import kotlinx.serialization.Serializable

@Serializable
data class FeatureResponse(
    val type: String,
    val geometry: GeometryResponse,
    val properties: PropertiesResponse
)
