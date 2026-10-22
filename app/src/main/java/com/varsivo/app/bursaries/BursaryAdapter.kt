package com.varsivo.app.bursaries

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.varsivo.app.R
import com.varsivo.app.databinding.ItemBursaryBinding

class BursaryAdapter : RecyclerView.Adapter<BursaryAdapter.ViewHolder>() {

    private var rows: List<BursaryRow> = emptyList()

    fun submit(newRows: List<BursaryRow>) {
        rows = newRows
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemBursaryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBursaryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = rows.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = rows[position]
        holder.binding.tvBursaryName.text = row.name
        holder.binding.tvBursaryDescription.text = row.description
        holder.binding.tvBursaryCloses.text = row.closes
        holder.binding.root.setBackgroundResource(
            if (row.urgent) R.drawable.bg_card_peach else R.drawable.bg_card_light
        )
    }
}