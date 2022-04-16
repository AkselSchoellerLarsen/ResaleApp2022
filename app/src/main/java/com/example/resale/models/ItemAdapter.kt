package com.example.resale.models

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resale.R
import com.example.resale.util.DateConverter
import retrofit2.http.Url
import java.lang.Exception
import java.net.URL
import java.net.URLConnection

class ItemAdapter(
    private val dataSet: List<Item>,
    private val onItemClicked: (position: Int) -> Unit
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item, viewGroup, false)

        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textViewId.text = dataSet[position].id.toString()
        viewHolder.textViewTitle.text = dataSet[position].title
        viewHolder.textViewDescription.text = dataSet[position].description
        viewHolder.textViewPrice.text = dataSet[position].price.toString()
        viewHolder.textViewSeller.text = dataSet[position].seller
        viewHolder.textViewDate.text = DateConverter.unixDateToJavaDate(dataSet[position].date).toString()

        try {
            val url = URL(dataSet[position].pictureUrl)
            val image = BitmapFactory.decodeStream(url.openConnection().getInputStream())
            viewHolder.imageViewPicture.setImageBitmap(image)
        } catch (e: Exception) {
            //Ignore
        } finally {
            if(viewHolder.imageViewPicture.drawable == null) {
                viewHolder.imageViewPicture.setImageResource(R.drawable.error)
            }
        }
    }

    override fun getItemCount() = dataSet.size

    class ViewHolder(view: View, private val onItemClicked: (position: Int) -> Unit) :
        RecyclerView.ViewHolder(view), View.OnClickListener {
        var textViewId: TextView = view.findViewById(R.id.textview_list_item_id)
        var textViewTitle: TextView = view.findViewById(R.id.textview_list_item_title)
        var textViewDescription: TextView = view.findViewById(R.id.textview_list_item_description)
        var textViewPrice: TextView = view.findViewById(R.id.textview_list_item_price)
        var textViewSeller: TextView = view.findViewById(R.id.textview_list_item_seller)
        var textViewDate: TextView = view.findViewById(R.id.textview_list_item_date)
        var imageViewPicture: ImageView = view.findViewById(R.id.imageView_list_item_picture)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = bindingAdapterPosition
            onItemClicked(position)
        }
    }
}