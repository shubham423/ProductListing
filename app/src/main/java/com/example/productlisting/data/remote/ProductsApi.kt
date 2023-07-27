package com.example.productlisting.data.remote

import com.example.productlisting.data.model.AddProductRequest
import com.example.productlisting.data.model.AddProductResponse
import com.example.productlisting.data.model.Product
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

interface ProductsApi {

    @GET("api/public/get")
    suspend fun getMyCardsScreenData(): Response<List<Product>>

    @Multipart
    @POST("api/public/add")
    suspend fun addProduct(
        @Part("product_type") productType: RequestBody,
        @Part("product_name") productName: RequestBody,
        @Part("price") sellingPrice: RequestBody,
        @Part("tax") taxRate: RequestBody,
        @Part file: MultipartBody.Part?=null
    ): Response<AddProductResponse>
}