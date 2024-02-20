package com.priximmo.model

import com.priximmo.dataclass.mutation.GeoMutationData

class ViewModelDisplayMutation () {

    fun getMinMaxFromList(mutationData: MutableList<GeoMutationData>): Array<Float?> {
        var sortedlistofMutation =
            mutationData.sortedWith(compareBy { it.valeurFonciere }).toMutableList()
        return arrayOf(
            sortedlistofMutation[0].valeurFonciere,
            sortedlistofMutation[sortedlistofMutation.size - 1].valeurFonciere
        )
    }

    fun getMinMaxSurface(mutationData: MutableList<GeoMutationData>): Array<Int> {
        var sortedlistofMutation = mutationData.sortedWith(compareByDescending{it.surfaceBien}).toMutableList()
        return arrayOf(sortedlistofMutation[0].surfaceBien, sortedlistofMutation[sortedlistofMutation.size-1].surfaceBien)
    }
}