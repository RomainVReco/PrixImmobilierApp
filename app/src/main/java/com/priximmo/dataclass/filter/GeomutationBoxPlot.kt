package com.priximmo.dataclass.filter

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GeomutationBoxPlot(val valFonciereMin: Float, val valFonciereMax: Float, var nbrLots: Int,
    val surfaceMin : Int, val surfaceMax: Int, val anneMutationMin: Int) : Parcelable {

    companion object Factory{
        val keyBoxPlot = "Box plot to filter"
    }

    }

