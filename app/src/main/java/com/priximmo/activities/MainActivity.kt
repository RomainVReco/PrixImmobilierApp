package com.priximmo.activities

import com.priximmo.retrofitapi.address.AddressAPI
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.priximmo.R
import com.priximmo.adapter.AddressAdapter
import com.priximmo.databinding.ActivityMainBinding
import com.priximmo.dataclass.addressBAN.AddressData
import com.priximmo.geojson.adresseban.AddressBAN
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class
MainActivity : AppCompatActivity(), Slider.OnChangeListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView : SearchView
    private lateinit var addressAdapter: AddressAdapter
    private var defaultYear = (LocalDateTime.now().year-2).toFloat()
    private var mList : MutableList<AddressData> = ArrayList()
    private val Tag: String = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(Tag, "onCreate")
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        searchView = findViewById(R.id.searchBarId)
        initViewComponents()
        initSearchView()
        initChipGroup()

        addressAdapter = AddressAdapter(mList)
        addressAdapter.setYearChange(defaultYear.toInt())
        addressAdapter.setChip(0)
        recyclerView = findViewById(R.id.mainActivityRecycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = addressAdapter
        binding.slideBarAnnee.addOnChangeListener(this)
    }

    private fun initChipGroup() {
        binding.chipMinimumYear.tag = 1
        binding.chipMinimumYear.setOnClickListener{
            addressAdapter.setChip(1)}
        Log.d("Chip_1_AnneeMinMut", getString(R.string.chip_minimum_year))

        binding.chipSingleYear.tag = 0
        binding.chipSingleYear.setOnClickListener { addressAdapter.setChip(0) }
        Log.d("Chip_2_AnneeMin", getString(R.string.chip_single_year))
    }

    private fun initViewComponents() {
        binding.slideBarAnnee.value = defaultYear
        val selectedValue = binding.slideBarAnnee.value.toInt()
        binding.selectedYearText.text = getString(R.string.selected_year, selectedValue)
    }

    private fun initSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                queryAddressFromText(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.length>4){
                    queryAddressFromText(newText)
                    return true
                }
                return false
            }
        })
    }

    override fun onValueChange(slider: Slider, value: Float, fromUser: Boolean) {
        Log.d("SliderDebug", "onValueChange - value : ${value.toInt()}")
        var year = value.toInt()
        if (fromUser) {
            binding.selectedYearText.text = getString(R.string.selected_year,year)
            addressAdapter.setYearChange(year)
        }
    }

    private fun queryAddressFromText(query: String?) {
        GlobalScope.launch(Dispatchers.IO) {
            if (query != null) {
                Log.d(Tag, "QueryNotNull")
                callAddressAPI(query)
            }
            Log.d(Tag, "QueryNull")
        }
    }

    private fun fillAddressList(listOfAddress: AddressBAN) {
        Log.d(Tag, "fillAddressList")
        var listAddressSample: MutableList<AddressData> = ArrayList()
        if (listOfAddress.features.size>0) {
            for (addressFeature in listOfAddress.features) {
                    var addressSample = AddressData(addressFeature.properties.label, addressFeature.properties.context,
                        addressFeature.geometry.toString(), addressFeature.properties.postcode)
                    listAddressSample.add(addressSample)
                    Log.d("AdressSample", addressSample.toString())
            }
            addressAdapter.setResultSet(listAddressSample)
            Log.d(Tag, "Fin fillAddressList")
        }
    }

    fun callAddressAPI(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api-adresse.data.gouv.fr/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(AddressAPI::class.java)

        val call = service.searchAddress(query)
        call.enqueue(object : Callback<AddressBAN> {
            override fun onResponse(call: Call<AddressBAN>, response: Response<AddressBAN>) {
                if (response.isSuccessful) {
                    Log.d(Tag, response.code().toString())
                    Log.d(Tag, response.body().toString())
                    val addressResponse = response.body()
                    if (addressResponse != null) {
                        fillAddressList(addressResponse)
                    }
                } else {
                    println("API request failed. Response Code: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<AddressBAN>, t: Throwable) {
                Log.d(Tag, "onFailure")
                t.printStackTrace()
            }
        })
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