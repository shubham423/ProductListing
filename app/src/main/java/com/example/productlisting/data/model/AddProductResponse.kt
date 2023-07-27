package com.example.productlisting.data.model

import com.google.gson.annotations.SerializedName

data class AddProductResponse (

    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("product_id")
    val productId: Int? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("product_details")
    val productDetails: ProductDetails? = null
)

data class ProductDetails(

    @SerializedName("image")
    val image: String? = null,

    @SerializedName("product_type")
    val productType: String? = null,

    @SerializedName("price")
    val price: Any? = null,

    @SerializedName("tax")
    val tax: Any? = null,

    @SerializedName("product_name")
    val productName: String? = null
)