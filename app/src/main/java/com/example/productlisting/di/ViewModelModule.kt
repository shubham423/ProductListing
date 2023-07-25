package com.example.productlisting.di


import com.example.productlisting.viewmodels.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ProductListViewModel(get())
    }
}