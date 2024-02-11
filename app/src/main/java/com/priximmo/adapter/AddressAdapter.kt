package com.priximmo.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.dataclass.addressBAN.AddressData
import java.time.LocalDateTime

class AddressAdapter (private var listAddressData: MutableList<AddressData>): RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    private val Tag: String = "AddressAdapter"
    private var yearToSearch: Int = 0

    class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelAddress = itemView.findViewById<TextView>(R.id.mainRecyclerLabel)
        val contextAddress = itemView.findViewById<TextView>(R.id.mainRecyclerContext)
        val container = itemView.findViewById<LinearLayout>(R.id.layoutContainerMain)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d(Tag, "onCreateViewHolder")
        val inflater = LayoutInflater.from(parent.context)
        val viewitem = inflater.inflate(R.layout.item_recycler_main_activity, parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(Tag, "onBindViewHolder")
        holder.labelAddress.text = listAddressData[position].label
        holder.contextAddress.text = listAddressData[position].context
        holder.container.setOnClickListener{ view ->
            Log.d(Tag, "ClickOnAddress")
            AddressData.launchDetailAddressActivity(listAddressData[position], view, yearToSearch)
        }
    }

    override fun getItemCount(): Int {
        return listAddressData.size
    }

    fun setResultSet(mList: MutableList<AddressData>) {
        this.listAddressData = mList
        notifyDataSetChanged()
    }
    fun setYearChange(newValue: Int) {
        this.yearToSearch = newValue
        Log.d(Tag, "setYearChange : ${this.yearToSearch}")
    }


}
