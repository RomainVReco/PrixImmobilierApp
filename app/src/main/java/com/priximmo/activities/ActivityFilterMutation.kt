package com.priximmo.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.iterator
import com.google.android.material.chip.Chip
import com.google.android.material.slider.RangeSlider
import com.google.android.material.slider.Slider
import com.priximmo.R
import com.priximmo.databinding.ActivityFilterMutationBinding
import com.priximmo.dataclass.filter.FilteredBoxPlot
import com.priximmo.dataclass.filter.GeomutationBoxPlot

class ActivityFilterMutation : AppCompatActivity() {

    lateinit var mutationData: GeomutationBoxPlot
    private lateinit var binding: ActivityFilterMutationBinding
    private lateinit var filteredBoxPlot: GeomutationBoxPlot

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFilterMutationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mutationData = intent.getParcelableExtra(GeomutationBoxPlot.keyBoxPlot)!!

        initInputValeurText()
        initSlideBarSurface()
        initValiderButton()

    }

    private fun initValiderButton() {
        binding.validateFilter.setOnClickListener {
            var minValeurFonciere = mutationData.valFonciereMin
            var maxValeurFonciere = mutationData.valFonciereMax
            if (binding.editMinPrice.text.toString().isNotBlank()) minValeurFonciere = binding.editMinPrice.text.toString().toFloat()
            if (binding.editMaxPrice.text.toString().isNotBlank()) maxValeurFonciere = binding.editMaxPrice.text.toString().toFloat()
            var nbrLot = "0"
            for (i in 0 until binding.chipGroup.childCount) {
                val chip = binding.chipGroup.getChildAt(i) as Chip
                if (chip.isChecked) {
                    nbrLot = chip.tag.toString()
                    break
                }
            }
            val minMax = binding.surfaceSliderBar.values
            val surfaceMin = minMax[0].toInt()
            val surfaceMax = minMax[1].toInt()
            val filteredBoxPlot = GeomutationBoxPlot(minValeurFonciere, maxValeurFonciere, nbrLot.toInt(),
                surfaceMin, surfaceMax, mutationData.anneMutationMin)
            val intent = Intent()
            intent.putExtra("KeyFilter", filteredBoxPlot)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }

    private fun initInputValeurText() {
        binding.editMinPrice.hint = mutationData.valFonciereMin.toString()
        binding.editMaxPrice.hint = mutationData.valFonciereMax.toString()
    }

    private fun initSlideBarSurface() {
        binding.surfaceSliderBar.valueFrom = mutationData.surfaceMin.toFloat()
        binding.surfaceSliderBar.valueTo = mutationData.surfaceMax.toFloat()

        binding.surfaceMin.text = getString(R.string.surface_max, mutationData.surfaceMin)
        binding.surfaceMax.text = getString(R.string.surface_max, mutationData.surfaceMax)

        binding.surfaceSliderBar.addOnSliderTouchListener(object: RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) {
                var minMax = slider.values
                Log.d("RangSlider From", minMax[0].toString())
                Log.d("RangSlider To", minMax[1].toString())
                binding.surfaceMin.text = getString(R.string.surface_min,minMax[0].toInt())
                binding.surfaceMax.text = getString(R.string.surface_min,minMax[1].toInt())
            }
            override fun onStopTrackingTouch(slider: RangeSlider) {
                var minMax = slider.values
                binding.surfaceMin.text = getString(R.string.surface_min,minMax[0].toInt())
                binding.surfaceMax.text = getString(R.string.surface_min,minMax[1].toInt())
            }

        })
    }
}