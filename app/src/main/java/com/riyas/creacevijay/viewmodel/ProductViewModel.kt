package com.riyas.creacevijay.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.riyas.creacevijay.AddProductFragment
import com.riyas.creacevijay.HomeFragment
import com.riyas.creacevijay.db.Product
import com.riyas.creacevijay.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel(private val repository: ProductRepository):ViewModel(),Observable {

    val products=repository.products

    @Bindable
    val inputProductName=MutableLiveData<String>()

    val saveProductButton=MutableLiveData<String>()

    init {
        saveProductButton.value="Add"
    }

    fun insertOrClear(){
        val productName= inputProductName.value!!
        insert(Product(0,productName))
        inputProductName.value=null
  


    }
    fun insert(product: Product) =viewModelScope.launch {
            repository.insert(product)
        }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }


}