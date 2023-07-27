package com.example.productlisting.data.model

import java.io.File

data class AddProductRequest (
    val productType: String,
    val productName: String,
    val sellingPrice: Double,
    val taxRate: Double,
    val productFilePath: File?
)