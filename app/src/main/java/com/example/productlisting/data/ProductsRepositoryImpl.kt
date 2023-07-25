package com.example.productlisting.data

import com.example.productlisting.data.model.Product
import com.example.productlisting.data.remote.ProductsApi
import retrofit2.Response

class ProductsRepositoryImpl(private val api: ProductsApi) : ProductsRepository {
    override suspend fun getProductsList(): Response<List<Product>> {
        return api.getMyCardsScreenData()
    }
}