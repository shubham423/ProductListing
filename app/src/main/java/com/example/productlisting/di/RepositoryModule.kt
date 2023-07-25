package com.example.productlisting.di

import com.example.productlisting.data.ProductsRepositoryImpl
import org.koin.dsl.module

val repoModule = module {
    single {
        ProductsRepositoryImpl(get())
    }
}