package com.priximmo.dataclass

data class AbstractGeometry<T>(
    val type: String,
    val coordinates: T
) {
    override fun toString(): String {
        return "{\"type\": \"Point\",\"coordinates\":$coordinates}"
    }
}