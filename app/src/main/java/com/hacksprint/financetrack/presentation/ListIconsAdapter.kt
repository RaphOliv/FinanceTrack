package com.hacksprint.financetrack.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.hacksprint.financetrack.R

class ListIconsAdapter(
    private val icons: List<Int>,
    private val iconClickListener: IconClickListener
) : RecyclerView.Adapter<ListIconsAdapter.IconViewHolder>() {

    var selectedIconPosition: Int = 0

    fun updateSelectedIconPosition(position: Int) {
        selectedIconPosition = position
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.icon_selection_style, parent, false)
        return IconViewHolder(view)
    }

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val icon = icons[position]
        holder.iconImageView.setImageResource(icon)

        holder.selectionCircle.visibility = if (holder.adapterPosition == selectedIconPosition) View.VISIBLE else View.GONE

        holder.itemView.setOnClickListener {
            val previousSelectedPosition = selectedIconPosition
            selectedIconPosition = holder.adapterPosition
            notifyItemChanged(previousSelectedPosition)
            notifyItemChanged(this.selectedIconPosition)

            iconClickListener.onIconClicked(icon)

        }
    }

    override fun getItemCount(): Int {
        return icons.size
    }

    inner class IconViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iconImageView: ImageView = itemView.findViewById(R.id.icon_list)
        val selectionCircle: View = itemView.findViewById(R.id.circle_select)
    }

    interface IconClickListener {
        fun onIconClicked(iconResId: Int)
    }



}
