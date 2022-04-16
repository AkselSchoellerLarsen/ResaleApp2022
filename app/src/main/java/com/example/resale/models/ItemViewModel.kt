package com.example.resale.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.resale.repository.ItemRepository

class ItemViewModel : ViewModel() {
    private val repository = ItemRepository()
    val itemsLiveData: LiveData<List<Item>> = repository.itemsLiveData
    val errorMessageLiveData: LiveData<String> = repository.errorMessageLiveData
    val updateMessageLiveData: LiveData<String> = repository.updateMessageLiveData

    init {
        reload()
    }

    fun reload() {
        repository.getItems()
    }

    fun getById(id: Int): Item? {
        if(itemsLiveData.value != null) {
            for (item in itemsLiveData.value!!) {
                if (item.id == id) {
                    return item
                }
            }
        }
        return null
    }

    fun add(item: Item) {
        repository.addItem(item)
    }

    fun delete(id: Int) {
        repository.deleteItem(id)
    }

    fun update(item: Item) {
        repository.addItem(item)
        repository.deleteItem(item.id)
    }
}