package com.priximmo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.adapter.MutationAdapter
import com.priximmo.dataclass.addressBAN.AddressData


class MutationActivity : AppCompatActivity() {
    val Tag = "mutationActivity"
    lateinit var addressData: AddressData
    lateinit var recyclerMutation: RecyclerView
    lateinit var mutationAdapter: MutationAdapter
//    var listofMutation: MutableList<GeoMutationData> = MutableList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mutation)
        addressData = intent.getParcelableExtra(AddressData.keyAddressData)!!
        val parcelleTitle = findViewById<TextView>(R.id.parcelleAddressTitle)
        parcelleTitle.text = getString(R.string.parcelle_title, addressData.label)


    }
}