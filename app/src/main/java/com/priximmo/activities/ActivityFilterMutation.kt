package com.priximmo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.priximmo.R
import com.priximmo.databinding.ActivityFilterMutationBinding
import com.priximmo.dataclass.mutation.GeoMutationData

class ActivityFilterMutation : AppCompatActivity() {

    lateinit var mutationData: List<GeoMutationData>
    private lateinit var binding: ActivityFilterMutationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterMutationBinding.inflate(layoutInflater)
        setContentView(binding.root)



//        mutationData = intent.getParcelableExtra(GeoMutationData.KeyGeomutationData)!!

        val minMaxArray = getMinMaxFromList(mutationData)

        binding.editMinPrice.hint = minMaxArray[0].toString()
        binding.editMaxPrice.hint = minMaxArray[1].toString()

        binding.surfaceSliderBar.valueTo = getMaxSurface(mutationData)
        binding.surfaceMax.text = getString(R.string.surface_max, getMaxSurface(mutationData))
        binding.surfaceMin.text = getString(R.string.surface_max, 0)
    }

    private fun getMaxSurface(mutationData: List<GeoMutationData>): Float {
        return 0f
    }

    private fun getMinMaxFromList(mutationData: List<GeoMutationData>): Array<Float> {
        return arrayOf(1f,2f)
    }
}