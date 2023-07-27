package com.example.productlisting.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.productlisting.data.ProductsRepository
import com.example.productlisting.data.model.AddProductRequest
import com.example.productlisting.data.model.AddProductResponse
import com.example.productlisting.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddProductViewModel(private val repository: ProductsRepository) : ViewModel() {

    // MutableStateFlow for internal usage
    // StateFlow for external (read-only) usage
    private val _productFlow = MutableStateFlow<Resource<AddProductResponse>?>(null)
    val productFlow: StateFlow<Resource<AddProductResponse>?> = _productFlow

    fun addProduct(request: AddProductRequest) {
        viewModelScope.launch {
            _productFlow.emit(Resource.loading(null))
            repository.addProduct(request).let {
                if (it.isSuccessful) {
                    _productFlow.emit(Resource.success(it.body()))
                } else {
                    _productFlow.emit(Resource.error(it.errorBody().toString(), null))
                }
            }
        }
    }

}