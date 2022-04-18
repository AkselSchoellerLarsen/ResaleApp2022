package com.example.resale.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resale.R

@Deprecated("Use ItemAdapter instead.")
class Adapter<T>(
    private val dataSet: List<T>,
    private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.Adapter<Adapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)

        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textViewTitle.text = dataSet[position].toString()

    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(view: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        var textViewTitle: TextView = view.findViewById(R.id.textview_list_item_title)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}