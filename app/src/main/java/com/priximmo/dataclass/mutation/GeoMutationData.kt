package com.priximmo.dataclass.mutation

import android.os.Parcel
import android.os.Parcelable
import com.priximmo.geojson.geomutation.Geomutation

data class GeoMutationData(
    val libTypBien: String?,
    val dateCession: String?,
    val valeurFonciere: Float?,
    val surfaceBien: String?,
    val nombreLot: Int?,
    val venteVefa: Boolean?,
    val referenceParcelle: String?,
    val geomutationId: Int?
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readFloat(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Boolean::class.java.classLoader) as? Boolean,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(libTypBien)
        parcel.writeFloat(valeurFonciere!!)
        parcel.writeString(surfaceBien)
        parcel.writeString(dateCession)
        parcel.writeValue(nombreLot)
        parcel.writeValue(venteVefa)
        parcel.writeString(referenceParcelle)
        parcel.writeValue(geomutationId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GeoMutationData> {
        override fun createFromParcel(parcel: Parcel): GeoMutationData {
            return GeoMutationData(parcel)
        }

        override fun newArray(size: Int): Array<GeoMutationData?> {
            return arrayOfNulls(size)
        }
    }
}
