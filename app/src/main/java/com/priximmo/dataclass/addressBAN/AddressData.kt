package com.priximmo.dataclass.addressBAN

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.priximmo.activities.MutationActivity

data class AddressData(val label: String, val context: String, val geometry: String, val postCode: String): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(label)
        parcel.writeString(context)
        parcel.writeString(geometry)
        parcel.writeString(postCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AddressData> {
        val keyAddressData = "addressData"
        override fun createFromParcel(parcel: Parcel): AddressData {
            return AddressData(parcel)
        }

        override fun newArray(size: Int): Array<AddressData?> {
            return arrayOfNulls(size)
        }

        fun launchDetailAddressActivity(addressData: AddressData, view: View) {
            val intent = Intent(view.context, MutationActivity::class.java)
            intent.putExtra(keyAddressData, addressData)
            view.context.startActivity(intent)
        }
    }
}

