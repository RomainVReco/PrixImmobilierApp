package com.priximmo.activities

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import com.priximmo.retrofitapi.geomutation.GeomutationRetrofitAPI
import com.priximmo.servicepublicapi.CommuneAPI
import com.priximmo.servicepublicapi.FeuilleAPI
import com.priximmo.servicepublicapi.ParcelleAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URISyntaxException
import java.time.LocalDateTime
import java.util.Collections


class MutationActivity : AppCompatActivity() {
    val Tag = "mutationActivity"
//    lateinit var addressData: AddressData

     var addressData = AddressData("21 Rue du Capitaine Ferber 92130 Issy-les-Moulineaux",
         "92, Hauts-de-Seine, Île-de-France", "{\"type\": \"Point\",\"coordinates\":[2.266267,48.825897]}",
    "92130");
    lateinit var recyclerMutation: RecyclerView
    lateinit var mutationAdapter: MutationAdapter
    var listofMutation: MutableList<GeoMutationData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mutation)
//        addressData = intent.getParcelableExtra(AddressData.keyAddressData)!!
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
            val callAPI = com.priximmo.servicepublicapi.ParcelleAPI(mutableMapOf<String, String>("geom=" to geometry))
            Log.d(Tag, "getParcelleFromGeometry : "+callAPI.conn.responseCode.toString())
            val responseManager = ResponseManagerHTTP<Parcelle>()
            val parcelle = responseManager.getAPIReturn(callAPI, Parcelle::class.java).orElse(Parcelle())
            extractGeomutationFromParcelle(parcelle)
        }
    }

    private fun extractGeomutationFromParcelle(parcelleResponse: Parcelle) {
        Log.d(Tag, "extractGeomutationFromParcelle")
        val codeInsee = getCodeInseeFromPostCode(addressData.postCode)
//        val codeInsee = addressData.postCode
        var bbox: String?
        if (parcelleResponse.numberReturned==0) {
            Log.d(Tag, "no parcelle found with GeometryPoint. Trying with closestParcelle")
            val section: String = getNearestSection(codeInsee, addressData.geometry)
            Log.d(Tag, section)
            bbox = getParecelleBboxFromSection(codeInsee, section, addressData.geometry!!)
        } else {
            bbox = parcelleResponse.featuresTerrain[0].terrainProperties.convertBboxToString()
            }
        GlobalScope.launch(Dispatchers.IO) {
            getGeomutationsFromTerrain(bbox!!, codeInsee!!)
        }
    }

    @Throws(URISyntaxException::class, IOException::class)
    fun getGeomutationsFromTerrain(bboxOfFeuille: String, cityCode: String) {
        Log.d(Tag, "getGeomutationsFromTerrain")
        val currentYear = LocalDateTime.now().year

        val retrofit = GeomutationRetrofitAPI.getClient()

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
                    val geomutationApiResponse = response.body()
                    if (geomutationApiResponse != null) {
                        println(geomutationApiResponse.showGeomutationContent())
                        fillSetOfMutation(geomutationApiResponse)
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
    }

    private fun fillSetOfMutation(geomutationApiResponse: Geomutation) {
        Log.d(Tag, "fillSetOfMutation")
        for (fm in geomutationApiResponse.features) {
            val libTypBien = fm.geomutationPoperties.libtypbien
            val valeurFonciere = fm.geomutationPoperties.valeurfonc.toString() + " €"
            val surfaceBien = fm.geomutationPoperties.sbati.toString() + "m²"
            val nombreLot = fm.geomutationPoperties.nblocmut
            val venteVefa = fm.geomutationPoperties.isVefa
            val referenceParcelle = fm.geomutationPoperties.getlIdpar().toString()
            val geomutationId = fm.id
            val geomutationData = GeoMutationData(libTypBien, valeurFonciere, surfaceBien, nombreLot, venteVefa,
                referenceParcelle, geomutationId)
            listofMutation.add(geomutationData)
        }
        mutationAdapter.setResultSet(listofMutation)
        Log.d(Tag, "Fin fillSetOfMutation")
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
        Log.d(Tag, "getNearestSection")
        val mapOfQueries = mutableMapOf("code_insee=" to cityCode, "geom=" to geometryPoint)
        val callAPI = FeuilleAPI(mapOfQueries)
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
        var callAPI = ParcelleAPI(mutableMapOf("code_insee=" to cityCode, "section=" to section))
        val parcelleResponseManagerHTTP = ResponseManagerHTTP<Parcelle>()
        val optionalParcelle = parcelleResponseManagerHTTP.getAPIReturn(callAPI, Parcelle::class.java)
        return if (optionalParcelle.isPresent) {
            val parcelleToReview = optionalParcelle.get()
            checkForNearestParcelle(parcelleToReview, geometryPoint)
        } else throw NoParcelleException("Pas de parcelle trouvée")
    }

    fun checkForNearestParcelle(parcelleToReview: Parcelle, geometryPoint: String): String? {
        Log.d(Tag, "checkForNearestParcelle")
        var longLat: List<Double> = ArrayList()
        longLat = extractLatitudeLongitude(geometryPoint)
        val simplifiedParcelleList: MutableList<SimplifiedParcelle> = ArrayList()
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
        val bbox = simplifiedParcelleList[0].convertedBbox
        return bbox
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