package com.priximmo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.adapter.MutationAdapter
import com.priximmo.databinding.ActivityDisplayMutationsBinding
import com.priximmo.dataclass.addressBAN.AddressData
import com.priximmo.dataclass.mutation.GeoMutationData
import com.priximmo.exceptions.NoParcelleException
import com.priximmo.geojson.feuille.Feuille
import com.priximmo.geojson.geomutation.Geomutation
import com.priximmo.geojson.parcelle.OrderByDistanceReference
import com.priximmo.geojson.parcelle.Parcelle
import com.priximmo.geojson.parcelle.SimplifiedParcelle
import com.priximmo.model.ResponseManagerHTTP
import com.priximmo.retrofitapi.commune.CommuneAPI
import com.priximmo.retrofitapi.commune.CommuneRetrofitAPI
import com.priximmo.retrofitapi.geomutation.GeoMutationAPI
import com.priximmo.retrofitapi.geomutation.GeomutationRetrofitAPI
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
import java.util.Collections


class ActivityDisplayMutations : AppCompatActivity() {
    val Tag = "mutationActivity"
    lateinit var addressData: AddressData
    lateinit var recyclerMutation: RecyclerView
    lateinit var mutationAdapter: MutationAdapter
    lateinit var progressBar: ProgressBar
    lateinit var binding: ActivityDisplayMutationsBinding
    var chipSelected: Int = 0
    private var yearToSearch = 0
    var listofMutation: MutableList<GeoMutationData> = ArrayList()
    var isSorted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityDisplayMutationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        addressData = intent.getParcelableExtra(AddressData.keyAddressData)!!
        yearToSearch = intent.getIntExtra(AddressData.keyYearData,0)
        chipSelected = intent.getIntExtra(AddressData.keyChipSelected,0)
        Log.d(Tag, yearToSearch.toString())

        val parcelleTitle = findViewById<TextView>(R.id.parcelleAddressTitle)
        parcelleTitle.text = getString(R.string.parcelle_title, addressData.label)

        val toolbar = findViewById<Toolbar>(R.id.toolbarMutation)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progressBarMutation)

        recyclerMutation = findViewById(R.id.recyclerMutation)
        recyclerMutation.layoutManager = LinearLayoutManager(this)

        mutationAdapter = MutationAdapter(listofMutation)
        recyclerMutation.adapter = mutationAdapter

        getParcelleFromGeometry(addressData.geometry.toString())
        }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_mutation_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when(item.itemId) {
        R.id.toolbarMutationFilter -> {
            val intent = Intent(this, ActivityFilterMutation::class.java)
            startActivity(intent)
            true
        }
        R.id.toolbarMutationSearch -> {
            Toast.makeText(this, "Chercher", Toast.LENGTH_SHORT).show()
            true
        }
        R.id.toolbarMutationSort->{
            Log.d(Tag, "Sort list by valeur foncière")
            val sortedlistofMutation: MutableList<GeoMutationData>
            if (!isSorted) {
                sortedlistofMutation = listofMutation.sortedWith(compareByDescending{it.valeurFonciere}).toMutableList()
                isSorted = true
            } else {
                sortedlistofMutation = listofMutation.sortedWith(compareBy{it.valeurFonciere}).toMutableList()
                isSorted = false
            }
            mutationAdapter.setResultSet(sortedlistofMutation)
            true
        }
        else->{
            super.onOptionsItemSelected(item)
        }
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

        val retrofit = GeomutationRetrofitAPI.getClient()

        val apiService = retrofit.create(GeoMutationAPI::class.java)

        val codeInsee = cityCode
        val inBbox = bboxOfFeuille
        val annee = yearToSearch.toString()
        val call: Call<Geomutation>
        // 0 pour la recherche sur une année et 1 pour une recherche sur un intervalle
        if (chipSelected == 1) {
            call = apiService.searchGeomutationByAnneeMin(annee, inBbox, codeInsee)
            }
        else {
            call = apiService.searchGeomutationForSingleYear(annee, inBbox, codeInsee)
        }
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
                    println("Error: ${response.code()}")
                    println("Message: ${response.message()}")
                    println("Body: ${response.body()}")
                    println("Raw: ${response.raw()}")
                    println("Error body: ${response.errorBody()}")
                    println("Headers: ${response.headers()}")
                }
            }

            override fun onFailure(call: Call<Geomutation>, t: Throwable) {
                println("Network error: ${t.message}")
                Log.e("Network error: ", "Error : ${t.message}", t)
            }
        })
    }

    private fun fillSetOfMutation(geomutationApiResponse: Geomutation) {
        Log.d(Tag, "fillSetOfMutation")
        for (fm in geomutationApiResponse.features) {
            val libTypBien = fm.geomutationPoperties.libtypbien
            val valeurFonciere = fm.geomutationPoperties.valeurfonc
            val dateCession = fm.geomutationPoperties.datemut
            val surfaceBien = fm.geomutationPoperties.sbati.toString() + "m²"
            val nombreLot = fm.geomutationPoperties.nblocmut
            val venteVefa = fm.geomutationPoperties.isVefa
            val referenceParcelle = fm.geomutationPoperties.getlIdpar().toString()
            val geomutationId = fm.id
            val geomutationData = GeoMutationData(libTypBien, dateCession, valeurFonciere, surfaceBien, nombreLot, venteVefa,
                referenceParcelle, geomutationId)
            listofMutation.add(geomutationData)
        }
        mutationAdapter.setResultSet(listofMutation)
        progressBar.visibility = View.INVISIBLE
        Log.d(Tag, "Liste")
        val nbMutation = findViewById<TextView>(R.id.nombreMutation)
        nbMutation.text = resources.getQuantityString(R.plurals.nb_mutation, listofMutation.size, listofMutation.size, listofMutation.size)
        nbMutation.visibility = View.VISIBLE
        Log.d(Tag, "Fin fillSetOfMutation")
    }

    @Throws(URISyntaxException::class, IOException::class)
    private fun getCodeInseeFromPostCode(postCode: String): String {
        Log.d(Tag, "getCodeInseeFromPostCode")
        val retrofitCommuneAPI = CommuneRetrofitAPI.getClient()

        val apiService = retrofitCommuneAPI.create(CommuneAPI::class.java)

        val call = apiService.searchCommune(postCode)
        val requestUrl = call.request().url().toString()
        Log.d("getCodeInseeFromPostCode", "API URL: $requestUrl")

        val communeAPIResponse = call.execute().body()
        Log.d(Tag, "Commune API : $communeAPIResponse")
        return communeAPIResponse!![0].code
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
        Log.d(Tag, "Debut calcul parcelle la plus proche")
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
        Log.d(Tag, "Fin du calcul")
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

    override fun onPause() {
        super.onPause()
        Log.d(Tag, "onPause")
    }
    override fun onDestroy() {
        super.onDestroy()
        Log.d(Tag, "onDestroy")
    }
    override fun onRestart() {
        super.onRestart()
        Log.d(Tag, "onRestart")
    }
}