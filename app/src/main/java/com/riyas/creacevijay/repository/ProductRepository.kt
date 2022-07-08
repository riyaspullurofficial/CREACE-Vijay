package com.riyas.creacevijay.repository

import com.riyas.creacevijay.db.Product
import com.riyas.creacevijay.db.ProductDao

class ProductRepository(private val dao: ProductDao) {
    val products=dao.getAllProduct()


    suspend fun insert(product: Product){
        dao.insertDataDao(product)
    }
    suspend fun delete(product: Product) : Int{
        return dao.deleteProduct(product)

    }


}