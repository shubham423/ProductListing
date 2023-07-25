package com.example.productlisting.data.remote

import com.example.productlisting.data.model.Product
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {

    @GET("api/public/get")
    suspend fun getMyCardsScreenData(): Response<List<Product>>
}