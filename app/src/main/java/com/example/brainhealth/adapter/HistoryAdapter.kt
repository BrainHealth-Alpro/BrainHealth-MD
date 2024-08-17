package com.example.brainhealth.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.text.Html
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.core.text.HtmlCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.brainhealth.R
import com.example.brainhealth.databinding.ItemHistoryBinding
import com.example.brainhealth.di.db.HistoryItem
import com.example.brainhealth.ui.history.RecommendationActivity
import com.example.brainhealth.ui.history.RecommendationFragment

class HistoryAdapter: ListAdapter<HistoryItem, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val history = getItem(position)
        if (history != null) {
            holder.bind(history)
        }
    }

    class MyViewHolder(private val binding: ItemHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: HistoryItem) {
            Glide.with(itemView.context).load(convertURL(item.gambarPath)).into(binding.imgHistory)
            val tempName = trimQuotes(item.namaLengkapPasien)
            binding.tvName.text = tempName
            if (binding.tvName.text == "") binding.tvName.text = "unknown"
            binding.tvResult.text = Html.fromHtml("<b>Hasil:</b> ${item.hasil}",  HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.tvDesc.text = Html.fromHtml("<b>Jenis tumor:</b> ${if (item.jenisTumor == "notumor") "-" else item.jenisTumor}",  HtmlCompat.FROM_HTML_MODE_LEGACY)
            val (date, time) = convertDateTime(item.datetime)
            binding.tvDate.text = "Tanggal: $date"
            binding.tvTime.text = "Waktu: $time WIB"
            binding.tvType.text = getFileExtension(item.gambarPath)

            binding.cardView.setOnClickListener {
                val intent = Intent(itemView.context, RecommendationActivity::class.java)
                itemView.context.startActivity(intent)
            }
        }

        fun trimQuotes(input: String): String {
            val startIndex = input.indexOf("\"") + 1 // find the index after the first "
            val endIndex = input.indexOf("\"", startIndex) // find the index of the next "

            if (startIndex != -1 && endIndex != -1) {
                val substring = input.substring(startIndex, endIndex)
                return substring // Output: This is a substring
            }
            return input
        }

        fun convertURL(localPath: String): String {
            val baseUrl = "http://4.206.193.225"
            val relativePath = localPath.replace("/app", "")
            return "$baseUrl$relativePath"
        }

        @SuppressLint("DefaultLocale")
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

            // Extract hours and minutes from time
            val timeParts = time.split(":")
            val hours = timeParts[0].toInt()
            val minutes = timeParts[1]
            val seconds = timeParts[2]

            // Add 7 hours to the time
            val newHours = (hours + 7) % 24 // Ensure it wraps around within a day

            // Format the new time
            val newTime = String.format("%02d:%s:%s", newHours, minutes, seconds)

            return Pair(date, newTime)
        }

        private fun getFileExtension(url: String): String {
            if (url.endsWith("zip_img.jpg")) return "zip"
            return url.substringAfterLast('.', "")
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

