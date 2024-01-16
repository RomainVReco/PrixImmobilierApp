package com.priximmo.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.adapter.MutationAdapter
import com.priximmo.dataclass.addressBAN.AddressData
import com.priximmo.dataclass.mutation.GeoMutationData
import com.priximmo.exceptions.NoParcelleException
import com.priximmo.geojson.feuille.Feuille
import com.priximmo.geojson.geomutation.FeatureMutation
import com.priximmo.geojson.geomutation.Geomutation
import com.priximmo.geojson.parcelle.OrderByDistanceReference
import com.priximmo.geojson.parcelle.Parcelle
import com.priximmo.geojson.parcelle.SimplifiedParcelle
import com.priximmo.model.ResponseManagerHTTP
import com.priximmo.retrofitapi.geomutation.GeoMutationAPI
import com.priximmo.servicepublicapi.CommuneAPI
import com.priximmo.servicepublicapi.FeuilleAPI
import com.priximmo.servicepublicapi.GeomutationAPI
import com.priximmo.servicepublicapi.NextPageAPI
import com.priximmo.servicepublicapi.ParcelleAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.URISyntaxException
import java.time.LocalDateTime
import java.util.Collections
import java.util.Optional


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

    private fun getParcelleFromGeometry(geometry: String) {
        GlobalScope.launch(Dispatchers.IO) {
            val callAPI = com.priximmo.servicepublicapi.ParcelleAPI(geometry, "geom=")
            Log.d(Tag, "getParcelleFromGeometry : "+callAPI.conn.responseCode.toString())
            val responseManager = ResponseManagerHTTP<Parcelle>()
            val parcelle = responseManager.getAPIReturn(callAPI, Parcelle::class.java).orElse(Parcelle())
            extractGeomutationFromParcelle(parcelle)
        }
    }

    private fun extractGeomutationFromParcelle(parcelleResponse: Parcelle) {
        Log.d(Tag, "extractGeomutationFromParcelle")
//        val codeInsee = getCodeInseeFromPostCode(addressData.postCode)
        val codeInsee = addressData.postCode
        var bbox: String? = parcelleResponse.featuresTerrain[0].terrainProperties.convertBboxToString()
        Log.d(Tag, bbox!!)
        if (parcelleResponse.numberReturned==0) {
            Log.d(Tag, "no parcelle found with GeometryPoint. Trying with closestParcelle")
            val section: String = getNearestSection(codeInsee, addressData.geometry)
            bbox = getParecelleBboxFromSection(codeInsee, section, addressData.geometry!!)
        } else {
            GlobalScope.launch(Dispatchers.IO) {
                getGeomutationsFromTerrain(bbox, codeInsee!!)
            }
        }
    }

    @Throws(URISyntaxException::class, IOException::class)
    fun getGeomutationsFromTerrain(bboxOfFeuille: String, cityCode: String) {
        Log.d(Tag, "getGeomutationsFromTerrain")
        var setOfGeomutations: MutableList<FeatureMutation> = ArrayList()
        var geomutation: Geomutation
        val currentYear = LocalDateTime.now().year

        val retrofit = Retrofit.Builder()
            .baseUrl("https://apidf-preprod.cerema.fr/dvf_opendata/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(GeoMutationAPI::class.java)

        val codeInsee = cityCode
        val inBbox = bboxOfFeuille
        val anneemutMin = currentYear-2

        val call = apiService.searchGeomutation(anneemutMin.toString(), inBbox, codeInsee)
        Log.d(Tag, "appel API Geomutation")

        call.enqueue(object : Callback<Geomutation> {
            override fun onResponse(call: Call<Geomutation>, response: Response<Geomutation>) {
                if (response.isSuccessful) {
                    Log.d(Tag, response.code().toString())
                    Log.d(Tag, response.body().toString())
                    val mutationApiResponse = response.body()
                    // Process the API response here
                    if (mutationApiResponse != null) {
                        println(mutationApiResponse.showGeomutationContent())
                    }
                } else {
                    // Handle unsuccessful response
                    println("Error: ${response.code()}")
                    println("Message: ${response.message()}")
                    println("Body: ${response.body()}")
                    println("Raw: ${response.raw()}")
                    println("Error body: ${response.errorBody()}")
                    println("Headers: ${response.headers()}")
                }
            }

            override fun onFailure(call: Call<Geomutation>, t: Throwable) {
                // Handle network errors
                println("Network error: ${t.message}")
            }
        })
//        var callAPI = GeomutationAPI((currentYear-2).toString(), cityCode, bboxOfFeuille)
//        Log.d(Tag, "GeomutationAPI : "+callAPI.conn.responseCode)
//        Log.d(Tag, "GeomutationAPI : "+callAPI.conn.responseMessage)
//        val responseManagerGeomutation = ResponseManagerHTTP<Geomutation>()
//        val optionalGeomutation: Optional<Geomutation> = responseManagerGeomutation.getAPIReturn(
//            callAPI,
//            Geomutation::class.java
//        )
//        if (optionalGeomutation.isPresent) {
//            geomutation = optionalGeomutation.orElse(Geomutation())
//            println(geomutation.showGeomutationContent())
//            setOfGeomutations.addAll(geomutation.features)
//            while (geomutation.getNext() != null) {
//                var nextPageAPI = NextPageAPI(geomutation.getNext())
//                geomutation =
//                    responseManagerGeomutation.getAPIReturn(nextPageAPI, Geomutation::class.java).get()
//                println(geomutation.showGeomutationContent())
//                setOfGeomutations.addAll(geomutation.features)
//            }
//        } else {
//            println("Pas de mutation pout cette adresse")
//        }
    }

    @Throws(URISyntaxException::class, IOException::class)
    private fun getCodeInseeFromPostCode(postCode: String?): String {
        if (postCode != null) {
            Log.d(Tag, postCode)
        }
        val callAPI = CommuneAPI(postCode)
        Log.d(Tag, "Réponse CommuneAPI : "+callAPI.conn.responseCode)
        Log.d(Tag, "Réponse CommuneAPI : "+callAPI.conn.responseMessage)
        val jsonResponse: String = callAPI.readReponseFromAPI(callAPI.conn)
        Log.d(Tag, jsonResponse)
        return jsonResponse.substring(38, 43)
    }

    @Throws(
        IOException::class,
        URISyntaxException::class,
        NoParcelleException::class
    )
    fun getNearestSection(cityCode: String?, geometryPoint: String?): String {
        var callAPI = FeuilleAPI(cityCode, geometryPoint, 2)
        val feuilleResponseManagerHTTP = ResponseManagerHTTP<Feuille>()
        val optionalFeuille = feuilleResponseManagerHTTP.getAPIReturn(
            callAPI,
            Feuille::class.java
        )
        return if (optionalFeuille.isPresent && optionalFeuille.get().numberReturned != 0) {
            optionalFeuille.get().featuresTerrain[0].terrainProperties.section
        } else throw NoParcelleException("Pas de section trouvée")
    }




    @Throws(
        IOException::class,
        URISyntaxException::class,
        NoParcelleException::class
    )
    fun getParecelleBboxFromSection(cityCode: String?, section: String?, geometryPoint: String): String? {
        Log.d(Tag, "getParecelleBboxFromSection")
        var callAPI = ParcelleAPI(cityCode, section)
        val parcelleResponseManagerHTTP = ResponseManagerHTTP<Parcelle>()
        val optionalParcelle = parcelleResponseManagerHTTP.getAPIReturn(callAPI, Parcelle::class.java)
        return if (optionalParcelle.isPresent) {
            val parcelleToReview = optionalParcelle.get()
            checkForNearestParcelle(parcelleToReview, geometryPoint)
        } else throw NoParcelleException("Pas de parcelle trouvée")
    }



    fun checkForNearestParcelle(parcelleToReview: Parcelle, geometryPoint: String): String? {
        Log.d(Tag, "checkForNearestParcelle")
        var longLat: List<Double> = java.util.ArrayList()
        longLat = extractLatitudeLongitude(geometryPoint)
        val simplifiedParcelleList: MutableList<SimplifiedParcelle> = java.util.ArrayList()
        for (featureTerrain in parcelleToReview.featuresTerrain) {
            val subparcelle = SimplifiedParcelle()
            subparcelle.bbox = featureTerrain.terrainProperties.bbox
            subparcelle.geometryPolygon = featureTerrain.geometry
            subparcelle.id = featureTerrain.terrainProperties.idu
            subparcelle.convertedBbox = featureTerrain.terrainProperties.convertBboxToString()
            // racine((xb-xa)²+(yb-ya)²)
            val distanceMinEucliX = Math.pow(subparcelle.bbox[0] - longLat[0], 2.0)
            val distanceMinEucliY = Math.pow(subparcelle.bbox[1] - longLat[1], 2.0)
            val distanceMinLongitude = Math.sqrt(distanceMinEucliX + distanceMinEucliY)
            val distanceMaxEucliX = Math.pow(subparcelle.bbox[2] - longLat[0], 2.0)
            val distanceMaxEucliY = Math.pow(subparcelle.bbox[3] - longLat[1], 2.0)
            val distanceMaxLongitude = Math.sqrt(distanceMaxEucliX + distanceMaxEucliY)
            subparcelle.distanceFromReference =
                Math.abs(distanceMinLongitude) + Math.abs(distanceMaxLongitude)
            simplifiedParcelleList.add(subparcelle)
        }
        Collections.sort(simplifiedParcelleList, OrderByDistanceReference())
        println(simplifiedParcelleList[0].toString())
        return simplifiedParcelleList[0].convertedBbox
    }

    private fun extractLatitudeLongitude(geometryPoint: String): List<Double> {
        Log.d(Tag, "extractLatitudeLongitude")
        val firstBrace = geometryPoint.indexOf("[")
        val removeFirstBrace = geometryPoint.substring(firstBrace + 1)
        val secondBrace = removeFirstBrace.indexOf("]")
        val removeSecondBrace = removeFirstBrace.substring(0, secondBrace)
        val coordinatesToParse = removeSecondBrace.split(",")
        val doubleList: MutableList<Double> = java.util.ArrayList()
        doubleList.add(java.lang.Double.valueOf(coordinatesToParse[0]))
        doubleList.add(java.lang.Double.valueOf(coordinatesToParse[1]))
        return doubleList
    }
}