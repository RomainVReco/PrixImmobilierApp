package com.priximmo.dataclass.mutation

import android.os.Parcel
import android.os.Parcelable

data class GeoMutationData(
    val libTypBien: String?,
    val valeurFonciere: String?,
    val surfaceBien: String?,
    val nombreLot: String?,
    val venteVefa: String?,
    val referenceParcelle: String?
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(libTypBien)
        parcel.writeString(valeurFonciere)
        parcel.writeString(surfaceBien)
        parcel.writeString(nombreLot)
        parcel.writeString(venteVefa)
        parcel.writeString(referenceParcelle)
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
