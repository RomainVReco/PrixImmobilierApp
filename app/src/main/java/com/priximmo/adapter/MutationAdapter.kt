package com.priximmo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.dataclass.mutation.GeoMutationData

class MutationAdapter (private var listGeomutation: MutableList<GeoMutationData>): RecyclerView.Adapter<MutationAdapter.ViewHolder>() {
    val Tag: String = "MutationAdapter"

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val natureOperation = itemView.findViewById<TextView>(R.id.natureOperation)
        val valeurFonciere = itemView.findViewById<TextView>(R.id.valeurFonciere)
        val surfaceBien = itemView.findViewById<TextView>(R.id.surfaceBien)
        val nombreLot = itemView.findViewById<TextView>(R.id.nombreLot)
        val venteVefa = itemView.findViewById<TextView>(R.id.venteVefa)
        val referenceParcelle = itemView.findViewById<TextView>(R.id.referenceParcelle)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MutationAdapter.ViewHolder {
        Log.d(Tag, "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val viewitem = inflater.inflate(R.layout.item_recycler_mutation_activity, parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(Tag, "onBindViewHolder")
        val context = holder.itemView.context
        holder.natureOperation.text = context.getString(R.string.libTypBien, listGeomutation[position].libTypBien)
        holder.valeurFonciere.text = context.getString(R.string.valeurFonciere, listGeomutation[position].valeurFonciere)
        holder.surfaceBien.text = context.getString(R.string.surfaceBien, listGeomutation[position].valeurFonciere)
        holder.nombreLot.text = context.getString(R.string.nombreLot, listGeomutation[position].nombreLot)
        holder.venteVefa.text = context.getString(R.string.venteVEFA, listGeomutation[position].venteVefa)
        holder.referenceParcelle.text = context.getString(R.string.referenceParcelle, listGeomutation[position].referenceParcelle)
    }

    override fun getItemCount(): Int {
        Log.d(Tag, "getItemCount : "+listGeomutation.size)
        return  listGeomutation.size
    }


}