package com.example.productlisting.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productlisting.data.ProductsRepository
import com.example.productlisting.data.model.Product
import com.example.productlisting.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(private val repository: ProductsRepository) : ViewModel() {

    // MutableStateFlow for internal usage
    // StateFlow for external (read-only) usage
    private val _productFlow = MutableStateFlow<Resource<List<Product>>?>(null)
    val productFlow: StateFlow<Resource<List<Product>>?> = _productFlow

    init {
        getProduct()
    }
    fun getProduct() {
        viewModelScope.launch {
            _productFlow.emit(Resource.loading(null))
            repository.getProductsList().let {
                if (it.isSuccessful) {
                    _productFlow.emit(Resource.success(it.body()))
                } else {
                    _productFlow.emit(Resource.error(it.errorBody().toString(), null))
                }
            }
        }
    }
}