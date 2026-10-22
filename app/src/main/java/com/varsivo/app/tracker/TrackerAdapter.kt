package com.varsivo.app.tracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.varsivo.app.R
import com.varsivo.app.databinding.ItemApplicationBinding

class TrackerAdapter(
    private val onItemClick: (ApplicationEntry) -> Unit
) : RecyclerView.Adapter<TrackerAdapter.ViewHolder>() {

    private var items: List<ApplicationEntry> = emptyList()

    fun submit(newItems: List<ApplicationEntry>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemApplicationBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemApplicationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        val context = holder.itemView.context
        val statusLabel = context.resources.getStringArray(R.array.application_statuses)[item.status.ordinal]

        holder.binding.tvApplicationName.text = item.name
        holder.binding.tvApplicationStatus.text = context.getString(R.string.tracker_status_line, statusLabel)
        holder.binding.root.setBackgroundResource(
            when (item.status) {
                ApplicationStatus.OFFER_RECEIVED -> R.drawable.bg_card_green
                ApplicationStatus.UNSUCCESSFUL -> R.drawable.bg_card_grey
                else -> R.drawable.bg_card_light
            }
        )
        holder.binding.root.setOnClickListener { onItemClick(item) }
    }
}