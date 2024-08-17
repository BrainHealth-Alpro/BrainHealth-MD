package com.example.brainhealth.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.brainhealth.R
import com.example.brainhealth.di.db.HistoryData

class ItemRowAdapter(private val context: Context, private val items: List<HistoryData>) :
    RecyclerView.Adapter<ItemRowAdapter.ItemRowViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val dummyImg = R.drawable.otak

    inner class ItemRowViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val imgHistory: ImageView = view.findViewById(R.id.imgHistory)
        val tvResult: TextView = view.findViewById(R.id.tvResult)
        val tvDesc: TextView = view.findViewById(R.id.tvDesc)
        val cardView: CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemRowViewHolder {
        val view = inflater.inflate(R.layout.item_history, parent, false)
        return ItemRowViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemRowViewHolder, position: Int) {
        val currentItem = items[position]
        holder.tvName.text = currentItem.name
        holder.tvResult.text = currentItem.result
        holder.tvDesc.text = currentItem.description
        holder.imgHistory.setImageResource(dummyImg)
    }

    override fun getItemCount(): Int {
        return items.size
    }
}

