package com.example.brainhealth.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainhealth.databinding.ItemHistoryBinding
import com.example.brainhealth.di.db.HistoryItem

class HistoryAdapter : ListAdapter<HistoryItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val chat = getItem(position)
        if (chat != null) {
            holder.bind(chat)
        }
    }

    class MyViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: HistoryItem) {
            Glide.with(itemView.context).load(item.gambar).into(binding.imgHistory)
            binding.tvName.text = item.namaLengkapPasien
            binding.tvResult.text = item.hasil
            binding.tvDesc.text = item.jenisTumor
            val (date, time) = convertDateTime(item.datetime)
            binding.tvDate.text = date
            binding.tvTime.text = time
            binding.tvType.text = getFileExtension(item.gambar)
        }

        private fun convertDateTime(input: String): Pair<String, String> {
            // Split the input string by spaces
            val parts = input.split(" ")

            // Extract the date part and reformat it
            val day = parts[1]
            val month = parts[2]
            val year = parts[3]
            val time = parts[4]

            // Map month name to month number
            val monthNumber = mapOf(
                "Jan" to "01", "Feb" to "02", "Mar" to "03", "Apr" to "04",
                "May" to "05", "Jun" to "06", "Jul" to "07", "Aug" to "08",
                "Sep" to "09", "Oct" to "10", "Nov" to "11", "Dec" to "12"
            )[month] ?: "00"

            // Create the date variable in the format dd/MM/yyyy
            val date = "$day/$monthNumber/$year"

            return Pair(date, time)
        }

        private fun getFileExtension(url: String): String {
            return url.substringAfterLast('.', missingDelimiterValue = "").substringBeforeLast('#', missingDelimiterValue = "")
        }

    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoryItem>() {
            override fun areItemsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoryItem,
                newItem: HistoryItem
            ): Boolean {
                return oldItem == newItem
            }
        }

    }
}

