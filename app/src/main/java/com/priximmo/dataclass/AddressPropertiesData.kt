package com.priximmo.dataclass

data class AddressPropertiesData(
   val label: String,
    val score: Double,
    val housenumber: String,
    val id: String,
    val banId: String,
    val name: String,
    val postcode: String,
    val citycode: String,
    val oldcitycode: String,
   val x: Double,
    val y: Double,
    val city: String,
    val oldcity: String,
    val district: String,
    val context: String,
    val propertyType: String,
    val importance: Double,
    val street: String
) {
    // Add additional methods or customization if needed

    override fun toString(): String {
        return "AddressProperties{" +
                "label='$label'" +
                ", score=$score" +
                ", id='$id'" +
                ", citycode='$citycode'" +
                ", context='$context'" +
                '}'
    }
}
