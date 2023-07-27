package com.example.productlisting.data

import com.example.productlisting.data.model.AddProductRequest
import com.example.productlisting.data.model.AddProductResponse
import com.example.productlisting.data.model.Product
import retrofit2.Call
import retrofit2.Response

interface ProductsRepository {
    suspend fun getProductsList():Response<List<Product>>
    suspend fun addProduct(addProductRequest: AddProductRequest):Response<AddProductResponse>
}