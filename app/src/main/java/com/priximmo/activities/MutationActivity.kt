package com.priximmo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.adapter.MutationAdapter
import com.priximmo.dataclass.addressBAN.AddressData
import com.priximmo.dataclass.mutation.GeoMutationData
import com.priximmo.geojson.geomutation.Geomutation
import com.priximmo.geojson.parcelle.Parcelle
import com.priximmo.retrofitapi.GeoMutationService
import com.priximmo.retrofitapi.ParcelleService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime


class MutationActivity : AppCompatActivity() {
    val Tag = "mutationActivity"
    lateinit var addressData: AddressData
    lateinit var recyclerMutation: RecyclerView
    lateinit var mutationAdapter: MutationAdapter
    var listofMutation: MutableList<GeoMutationData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mutation)
        addressData = intent.getParcelableExtra(AddressData.keyAddressData)!!
        val parcelleTitle = findViewById<TextView>(R.id.parcelleAddressTitle)
        parcelleTitle.text = getString(R.string.parcelle_title, addressData.label)

        recyclerMutation = findViewById(R.id.recyclerMutation)
        recyclerMutation.layoutManager = LinearLayoutManager(this)

        mutationAdapter = MutationAdapter(listofMutation)
        recyclerMutation.adapter = mutationAdapter

        getParcelleFromGeometry(addressData.geometry.toString())
        }

    private fun extractParcelleFromAddress(parcelleResponse: Parcelle) {
        Log.d(Tag, "extractParcelleFromAddress")
//        val codeInsee = getCodeInseeFromPostCode(addressData.postCode)
        val currentYear = LocalDateTime.now().year
        val bbox = parcelleResponse.bbox.toString()
        if (parcelleResponse.numberReturned==0) {
            Log.d(Tag, "no parcelle found with GeometryPoint. Trying with closestParcelle")
            tryGetClosestParcelle(parcelleResponse.bbox.toString())
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("https://apidf-preprod.cerema.fr/dvf_opendata/geomutations/?")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

                val service = retrofit.create(GeoMutationService::class.java)
                val call = service.searchGeomutation("2022", bbox, "92040")

                call.enqueue(object : Callback<Geomutation> {
                    override fun onResponse(call: Call<Geomutation>, response: Response<Geomutation>) {
                        if (response.isSuccessful) {
                            Log.d(Tag, response.code().toString())
                            Log.d(Tag, response.body().toString())
                            val geoMutationResponse = response.body()
                            if (geoMutationResponse != null) {
                                fillGeomutationList(geoMutationResponse)
                            }
                        } else Log.d(Tag,"API request failed. Response Code: ${response.code()}")
                    }

                    override fun onFailure(call: Call<Geomutation>, t: Throwable) {
                        Log.d(Tag, "onFailure")
                        t.printStackTrace()
                    }
                })
                
            }
            
        }

    }

    private fun fillGeomutationList(geoMutationResponse: Geomutation) {

    }

    private fun tryGetClosestParcelle(bbox: String) {
        

    }

    private fun getParcelleFromGeometry(geometry: String) {
        Log.d(Tag, "getParcelleFromGeometry")
        GlobalScope.launch(Dispatchers.IO) {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://apicarto.ign.fr/api/cadastre/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(ParcelleService::class.java)
            val call = service.searchParcelle(geometry)

            call.enqueue(object : Callback<Parcelle> {
                override fun onResponse(call: Call<Parcelle>, response: Response<Parcelle>) {
                    if (response.isSuccessful) {
                        Log.d(Tag, response.code().toString())
                        Log.d(Tag, response.body().toString())
                        val parcelleResponse = response.body()
                        if (parcelleResponse != null) {
                            extractParcelleFromAddress(parcelleResponse)
                        }
                    } else Log.d(Tag,"API request failed. Response Code: ${response.code()}")
                }

                override fun onFailure(call: Call<Parcelle>, t: Throwable) {
                    Log.d(Tag, "onFailure")
                    t.printStackTrace()
                }
            })
        }
    }
}