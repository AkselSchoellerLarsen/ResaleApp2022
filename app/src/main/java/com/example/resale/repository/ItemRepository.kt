package com.example.resale.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.resale.models.Item
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItemRepository {
    private val url = "https://anbo-restresale.azurewebsites.net/api/"

    private val itemDealerService: ItemDealerService
    val itemsLiveData: MutableLiveData<List<Item>> = MutableLiveData<List<Item>>()
    val errorMessageLiveData: MutableLiveData<String> = MutableLiveData()
    val updateMessageLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        val build: Retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()).build()
        itemDealerService = build.create(ItemDealerService::class.java)
        getItems()
    }

    fun getItems() {
        itemDealerService.getAllItems().enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful) {

                    itemsLiveData.postValue(response.body())
                    errorMessageLiveData.postValue("")
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("TagNotUsedByOthers", message)
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                //booksLiveData.postValue(null)
                errorMessageLiveData.postValue(t.message)
                Log.d("TagNotUsedByOthers", t.message!!)
            }
        })
    }

    /*
    fun getItem(id: Int) {
        itemDealerService.getItemById(id).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    if(response.body() != null) {
                        val items = listOf(response.body()!!)

                        itemsLiveData.postValue(items)
                        errorMessageLiveData.postValue("")
                    } else {
                        val message = "Failed to get item with id of" + id
                        errorMessageLiveData.postValue(message)
                        Log.d("TagNotUsedByOthers", message)
                    }
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("TagNotUsedByOthers", message)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("TagNotUsedByOthers", t.message!!)
            }
        })
    }
     */

    fun addItem(item: Item) {
        itemDealerService.addItem(item).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Log.d("TagNotUsedByOthers", "Added: " + response.body())
                    updateMessageLiveData.postValue("Added: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("TagNotUsedByOthers", message)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("TagNotUsedByOthers", t.message!!)
            }
        })
    }

    fun deleteItem(id: Int) {
        itemDealerService.deleteItem(id).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Log.d("TagNotUsedByOthers", "Deleted: " + response.body())
                    updateMessageLiveData.postValue("Deleted: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("TagNotUsedByOthers", message)
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("TagNotUsedByOthers", t.message!!)
            }
        })
    }

    /*
    fun updateItem(item: Item) {
        itemDealerService.updateItem(item.id, item).enqueue(object : Callback<Item> {
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                if (response.isSuccessful) {
                    Log.d("TagNotUsedByOthers", "Updated: " + response.body())
                    updateMessageLiveData.postValue("Updated: " + response.body())
                } else {
                    val message = response.code().toString() + " " + response.message()
                    errorMessageLiveData.postValue(message)
                    Log.d("TagNotUsedByOthers",
                        "Failed to update item \"$item\" because: $message"
                    )
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                errorMessageLiveData.postValue(t.message)
                Log.d("TagNotUsedByOthers", t.message!!)
            }
        })
    }
     */
}