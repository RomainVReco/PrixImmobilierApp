package com.priximmo.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.adapter.AddressAdapter
import com.priximmo.dataclass.AddressData
import com.priximmo.geojson.adresseban.AddressBAN
import com.priximmo.model.ResponseManagerHTTP
import com.priximmo.servicepublicapi.AdresseAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : androidx.appcompat.widget.SearchView
    private lateinit var addressAdapter: AddressAdapter
    private var mList = ArrayList<AddressData>()
    private val Tag: String = "MainActivity1"




    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressAdapter = AddressAdapter(mList)
        recyclerView = findViewById(R.id.mainActivityRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = addressAdapter

        searchView = findViewById(R.id.searchBarId)
        searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryAddressFromText(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun queryAddressFromText(query: String?) {
        GlobalScope.launch(Dispatchers.IO) {
            val callAddressAPI = AdresseAPI(query)
            val responseManager = ResponseManagerHTTP<AddressBAN>()
            val optionalAdressBan = responseManager.getAPIReturn(callAddressAPI, AddressBAN::class.java)
            fillAddressList(optionalAdressBan.orElse(AddressBAN()))
        }
    }

    private fun fillAddressList(listOfAddress: AddressBAN) {
        mList.clear()
        if (listOfAddress.features.size > 0) {
            for (addressFeature in listOfAddress.features) {
                var addressSample = AddressData(addressFeature.properties.label, addressFeature.properties.context,
                    addressFeature.geometry.toString())
                mList.add(addressSample )
            }
            addressAdapter.setResultSet(mList)
        }
    }

}