package com.varsivo.app.explore

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.varsivo.app.R
import com.varsivo.app.databinding.ItemExploreBinding

class ExploreAdapter : RecyclerView.Adapter<ExploreAdapter.ViewHolder>() {

    private var items: List<ExploreItem> = emptyList()

    fun submit(newItems: List<ExploreItem>) {
        items = newItems
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: ItemExploreBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemExploreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvItemTitle.text = item.title
        holder.binding.tvItemSubtitle.text = item.subtitle
        holder.binding.root.setBackgroundResource(
            when (item.state) {
                ItemState.QUALIFIES -> R.drawable.bg_card_green
                ItemState.NOT_YET -> R.drawable.bg_card_peach
                ItemState.INFO -> R.drawable.bg_card_light
            }
        )
    }
}