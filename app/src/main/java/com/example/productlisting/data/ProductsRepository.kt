package com.example.productlisting.data

import com.example.productlisting.data.model.Product
import retrofit2.Response

interface ProductsRepository {
    suspend fun getProductsList():Response<List<Product>>
}