package com.priximmo.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.priximmo.R
import com.priximmo.dataclass.AddressData

class AddressAdapter (private var listAddressData: List<AddressData>): RecyclerView.Adapter<AddressAdapter.ViewHolder>() {
    val Tag: String = "AddressAdapter"

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelAddress = itemView.findViewById<TextView>(R.id.mainRecyclerLabel)
        val contextAddress = itemView.findViewById<TextView>(R.id.mainRecyclerContext)
        val container = itemView.findViewById<LinearLayout>(R.id.layoutContainerMain)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewitem = inflater.inflate(R.layout.item_recycler_main_activity, parent, false)
        return ViewHolder(viewitem)
    }

    override fun onBindViewHolder(holder: AddressAdapter.ViewHolder, position: Int) {
        holder.labelAddress.text = listAddressData[position].label
        holder.contextAddress.text = listAddressData[position].context
        holder.container.setOnClickListener{ view ->
            Toast.makeText(holder.itemView.context, listAddressData[position].geometry, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return listAddressData.size
    }

    fun setResultSet(mList: ArrayList<AddressData>) {
        this.listAddressData = mList
        notifyDataSetChanged()
    }


}
