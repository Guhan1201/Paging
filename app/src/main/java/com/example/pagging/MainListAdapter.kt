package com.example.pagging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class MainListAdapter : PagingDataAdapter<Data, MainListAdapter.ViewHolder>(DataDifferntiator) {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.textViewName)
        val textViewEmail: TextView = view.findViewById(R.id.textViewEmail)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder
        holder.textView.text =
            "${getItem(position)?.firstName} ${getItem(position)?.lastName}"
        holder.textViewEmail.text = getItem(position)?.email

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        )
    }

    object DataDifferntiator : DiffUtil.ItemCallback<Data>() {

        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

}