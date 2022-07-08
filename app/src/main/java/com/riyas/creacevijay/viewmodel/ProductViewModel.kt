package com.riyas.creacevijay.viewmodel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.fragment.app.Fragment
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



            val manager = AddProductFragment.addProManager
            val transaction = manager.beginTransaction()
            transaction.replace(com.riyas.creacevijay.R.id.fragmentFL, HomeFragment())
            transaction.addToBackStack(null)
            transaction.commit()


    }
    fun insert(product: Product) =viewModelScope.launch {
            repository.insert(product)
        }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    fun delete(product: Product) = viewModelScope.launch {
        val noOfRowsDeleted = repository.delete(product)

    /*    if(noOfRowsDeleted>0){
            inputName.value = null
            inputEmail.value = null
            isUpdateOrDelete = false
            saveOrUpdateButtonText.value = "Save"
            clearAllOrDeleteButtonText.value = "Clear All"
            statusMessage.value = Event("$noOfRowsDeleted Row Deleted Successfully")
        }else{
            statusMessage.value = Event("Error Occurred")
        }*/

    }

}