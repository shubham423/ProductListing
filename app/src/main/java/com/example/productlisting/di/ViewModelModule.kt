package com.example.productlisting.di


import com.example.productlisting.ui.viewmodels.AddProductViewModel
import com.example.productlisting.ui.viewmodels.ProductListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        ProductListViewModel(get())
    }
    viewModel {
        AddProductViewModel(get())
    }
}