package com.borislav.searchmyaddress.data.model.mapper

import com.borislav.searchmyaddress.data.model.FeatureResponse
import com.borislav.searchmyaddress.data.model.PropertiesResponse
import com.borislav.searchmyaddress.domain.model.Address

// in your mappers package
internal fun FeatureResponse.toDomain(): Address = Address(
    houseNumber = this.properties.housenumber ?: "",
    streetName = this.properties.street ?: "",
    postalCode = this.properties.postcode,
    city = this.properties.city,
    longitude = this.geometry.coordinates[0],
    latitude = this.geometry.coordinates[1]
)


