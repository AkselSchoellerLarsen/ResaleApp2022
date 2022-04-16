package com.example.resale.models

import com.example.resale.util.DateConverter
import java.util.*

data class Item(
    val id: Int,
    val title: String,
    val description: String,
    val price: Int,
    val seller: String,
    val date: Int,
    val pictureUrl: String?) {

    override fun toString(): String {
        return title + ": " + description + "\n" +
        "Costing " + price + "DKK\n" +
        "Seller: " + seller + "\n" +
        "Created on " + DateConverter.unixDateToJavaDate(date)
    }
}