package com.borislav.searchmyaddress.domain.model

data class Address(
    val houseNumber: String?,
    val streetName: String,
    val postalCode: String,
    val city: String,
    val longitude: Double,
    val latitude: Double
)
