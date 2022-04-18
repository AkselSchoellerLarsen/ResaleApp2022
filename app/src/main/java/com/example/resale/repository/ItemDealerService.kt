package com.example.resale.repository

import com.example.resale.models.Item
import retrofit2.Call
import retrofit2.http.*

interface ItemDealerService {
    @GET("ResaleItems")
    fun getAllItems(): Call<List<Item>>

    /*
    @GET("ResaleItems/{id}")
    fun getItemById(@Path("id") id: Int): Call<Item>
    */

    @POST("ResaleItems")
    fun addItem(@Body item: Item): Call<Item>

    @DELETE("ResaleItems/{id}")
    fun deleteItem(@Path("id") id: Int): Call<Item>

    /*
    @PUT("ResaleItems/{id}")
    fun updateItem(@Path("id") id: Int, @Body item : Item): Call<Item>
     */
}