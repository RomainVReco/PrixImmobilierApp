package com.priximmo.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.priximmo.R
import com.priximmo.databinding.ActivityFilterMutationBinding
import com.priximmo.dataclass.filter.GeomutationBoxPlot

class ActivityFilterMutation : AppCompatActivity() {

    lateinit var mutationData: GeomutationBoxPlot
    private lateinit var binding: ActivityFilterMutationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterMutationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mutationData = intent.getParcelableExtra(GeomutationBoxPlot.keyBoxPlot)!!

        binding.editMinPrice.hint = mutationData.valFonciereMin.toString()
        binding.editMaxPrice.hint = mutationData.valFonciereMax.toString()

        binding.surfaceSliderBar.valueFrom = mutationData.surfaceMin.toFloat()
        binding.surfaceSliderBar.valueTo = mutationData.surfaceMax.toFloat()
        binding.surfaceMax.text = getString(R.string.surface_max, mutationData.surfaceMax)
        binding.surfaceMin.text = getString(R.string.surface_max, mutationData.surfaceMin)

        binding.slideBarAnnee.valueFrom = mutationData.anneMutationMin.toFloat()
    }
}