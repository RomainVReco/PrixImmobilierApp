package com.priximmo.dataclass

data class AddressResponse(
    val type: String,
    val version: String,
    val features: List<FeatureAddressBANData>,
    val attribution: String,
    val licence: String,
    val query: String,
    val limit: Int
) {
    fun showAddressBANContent(): String {
        val sb = StringBuilder()
        val limitLine = "\n################################################\n\n"

        features.forEachIndexed { index, feature ->
            sb.append("Label : ${feature.properties.label},\n")
            sb.append("Code INSEE : ${feature.properties.citycode},\n")
            sb.append("Context : ${feature.properties.context},\n")
            sb.append("Geometry point : ${feature.geometry.toString()}\n")

            if (index < features.size - 1) {
                sb.append(limitLine)
            }
        }
        return sb.toString()
    }
}
