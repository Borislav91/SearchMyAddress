package com.borislav.searchmyaddress.data.model

import kotlinx.serialization.Serializable

@Serializable
data class GeometryResponse(
    val type: String,
    val coordinates: List<Double>
)