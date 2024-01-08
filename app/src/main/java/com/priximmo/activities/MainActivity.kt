package com.priximmo.activities

import AddressService
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.adapter.AddressAdapter
import com.priximmo.dataclass.AddressData
import com.priximmo.dataclass.AddressResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class
MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : SearchView
    private lateinit var addressAdapter: AddressAdapter
    private var mList : MutableList<AddressData> = ArrayList()
    private val Tag: String = "MainActivity"




    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addressAdapter = AddressAdapter(mList)
        recyclerView = findViewById(R.id.mainActivityRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = addressAdapter

        searchView = findViewById(R.id.searchBarId)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
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
//            val callAddressAPI = AdresseAPI(query)
//            val responseManager = ResponseManagerHTTP<AddressBAN>()
//            val optionalAdressBan = responseManager.getAPIReturn(callAddressAPI, AddressBAN::class.java)
//            fillAddressList(optionalAdressBan.orElse(AddressBAN()))
            if (query != null) {
                Log.d(Tag, "QueryNotNull")
                main(query)
            }
            Log.d(Tag, "QueryNull")
        }
    }

    private fun fillAddressList(listOfAddress: AddressResponse) {
        Log.d(Tag, "fillAddressList")
        var listAddressSample: MutableList<AddressData> = ArrayList()
        if (listOfAddress.features.size>0) {
            for (addressFeature in listOfAddress.features) {
                var addressSample = AddressData(addressFeature.properties.label, addressFeature.properties.context,
                    addressFeature.geometry.toString())
                listAddressSample.add(addressSample)
                Log.d("AdressSample", addressSample.toString())
            }
            addressAdapter.setResultSet(listAddressSample)
            Log.d(Tag, "Fin fillAddressList")
        }
    }

    fun main(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-adresse.data.gouv.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AddressService::class.java)

        val call = service.searchAddress(query)
        call.enqueue(object : Callback<AddressResponse> {
            override fun onResponse(call: Call<AddressResponse>, response: Response<AddressResponse>) {
                if (response.isSuccessful) {
                    Log.d(Tag, response.code().toString())
                    Log.d(Tag, response.message().toString())
                    Log.d(Tag, response.body().toString())
                    val addressResponse = response.body()
                    if (addressResponse != null) {
                        fillAddressList(addressResponse)
                    }
                } else {
                    println("API request failed. Response Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AddressResponse>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

}