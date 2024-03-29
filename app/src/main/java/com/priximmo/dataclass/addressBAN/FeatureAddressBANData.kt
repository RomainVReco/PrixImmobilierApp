package com.priximmo.dataclass.addressBAN


import com.priximmo.geojson.geometry.GeometryPoint

data class FeatureAddressBANData(
    val type: String,
    val geometry: GeometryPoint,
    val properties: AddressPropertiesData
) {
    fun showAddressLabel(): String {
        return properties.label
    }
}
