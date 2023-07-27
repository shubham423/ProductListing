package com.example.productlisting.data

import com.example.productlisting.data.model.AddProductRequest
import com.example.productlisting.data.model.AddProductResponse
import com.example.productlisting.data.model.Product
import com.example.productlisting.data.remote.ProductsApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response

class ProductsRepositoryImpl(private val api: ProductsApi) : ProductsRepository {
    override suspend fun getProductsList(): Response<List<Product>> {
        return api.getMyCardsScreenData()
    }

    override suspend fun addProduct(addProductRequest: AddProductRequest): Response<AddProductResponse> {
        val productTypeBody = addProductRequest.productType.toRequestBody("text/plain".toMediaTypeOrNull())
        val productNameBody = addProductRequest.productName.toRequestBody("text/plain".toMediaTypeOrNull())
        val sellingPriceBody = addProductRequest.sellingPrice.toString().toRequestBody("text/plain".toMediaTypeOrNull())
        val taxRateBody = addProductRequest.taxRate.toString().toRequestBody("text/plain".toMediaTypeOrNull())

        val requestFile = addProductRequest.productFilePath?.asRequestBody("image/*".toMediaTypeOrNull())
        val body= if (addProductRequest.productFilePath!=null) requestFile?.let {
            MultipartBody.Part.createFormData("files", addProductRequest.productFilePath.name,
                it
            )
        } else null

        return api.addProduct(productTypeBody,productNameBody,sellingPriceBody,taxRateBody,body)
    }
}