package com.example.resale.models

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.drawable.toIcon
import androidx.core.graphics.scale
import androidx.recyclerview.widget.RecyclerView
import com.example.resale.R
import com.example.resale.util.DateConverter
import java.net.URL

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

        var image: Bitmap? = null
        val imageThread = Thread {
            image = tryGetImage(dataSet[position].pictureUrl)
        }
        imageThread.start()

        while(imageThread.isAlive) {
            Thread.sleep(10)
        }

        if(image != null) {
            viewHolder.imageViewPicture.setImageBitmap(image)
        } else {
            viewHolder.imageViewPicture.setImageResource(R.drawable.error)
        }
    }
    private fun tryGetImage(stringUrl: String?) : Bitmap? {
        if (stringUrl != null && stringUrl != "") {
            try {
                val properUrl = URL(stringUrl)
                return BitmapFactory.decodeStream(properUrl.openConnection().getInputStream())
            } catch (e: Exception) {
                Log.d(
                    "TagNotUsedByOthers",
                    "Image from URL \n" +
                            "\"" + stringUrl + "\"\n" +
                            " could not be loaded because the following exception was thrown:\n" +
                            "$e"
                )
            }
        }
        return null
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