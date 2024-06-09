package com.example.com527_week3

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ShoppingListViewModel: ViewModel(){
    val shoppingList = mutableListOf<String>()

    fun addItem(item : String){
        shoppingList.add(item)
        liveShoppingList.value = shoppingList
    }

    val liveShoppingList = MutableLiveData<MutableList<String>>()

}