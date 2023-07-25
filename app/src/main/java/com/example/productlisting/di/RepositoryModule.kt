package com.example.productlisting.di

import com.example.productlisting.data.ProductsRepository
import com.example.productlisting.data.ProductsRepositoryImpl
import org.koin.dsl.module

val repoModule = module {
    single<ProductsRepository> {
      return@single ProductsRepositoryImpl(get())
    }
}