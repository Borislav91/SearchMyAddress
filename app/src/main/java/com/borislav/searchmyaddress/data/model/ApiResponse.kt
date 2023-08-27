package com.borislav.searchmyaddress.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponse(
    val type: String,
    val features: List<FeatureResponse>,
)
