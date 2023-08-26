package com.borislav.searchmyaddress.data.model.mapper

import com.borislav.searchmyaddress.data.model.PropertiesResponse
import com.borislav.searchmyaddress.domain.model.Address

// in your mappers package
internal fun PropertiesResponse.toDomain(): Address = Address(
    houseNumber = this.housenumber,
    streetName = this.street,
    postalCode = this.postcode,
    city = this.city
)

