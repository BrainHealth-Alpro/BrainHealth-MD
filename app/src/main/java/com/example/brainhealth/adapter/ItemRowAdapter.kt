package com.example.brainhealth.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainhealth.R

class ItemRowAdapter() :
    RecyclerView.Adapter<ItemRowAdapter.ItemRowViewHolder>() {
    class ItemRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val rvImages: RecyclerView = view.findViewById(R.id.imgHistory)
        val cardView: CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        return ItemRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemRowAdapter.ItemRowViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}