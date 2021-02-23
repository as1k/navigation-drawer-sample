package com.as1k.navigationdrawersample

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AppMenuItemsAdapter(
    val list: ArrayList<AppMenuItem>,
    val itemClickListener: RecyclerViewItemClick
) : RecyclerView.Adapter<AppMenuItemsAdapter.MenuItemHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemHolder {
        return MenuItemHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_app_menu, parent, false)
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MenuItemHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class MenuItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var menuItemTitle: TextView = itemView.findViewById(R.id.menuItemTitle)

        fun bind(menuItem: AppMenuItem?) = with(itemView) {
            menuItemTitle.text = menuItem?.name

            itemView.setOnClickListener {
                itemClickListener.onItemClick(adapterPosition)
            }
        }
    }
}
