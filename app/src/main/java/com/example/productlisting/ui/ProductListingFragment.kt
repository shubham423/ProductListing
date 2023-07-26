package com.example.productlisting.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.productlisting.data.model.Product
import com.example.productlisting.databinding.FragmentProductListingBinding
import com.example.productlisting.ui.adapter.ProductsListAdapter
import com.example.productlisting.ui.viewmodels.ProductListViewModel
import com.example.productlisting.utils.Status
import com.example.productlisting.utils.gone
import com.example.productlisting.utils.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListingFragment : Fragment() {
    private lateinit var binding: FragmentProductListingBinding
    private lateinit var adapter: ProductsListAdapter
    private val viewModel:ProductListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProductListingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= ProductsListAdapter()
        viewModel.getProducts()
        initObservers()
        initSearchView()
    }

    private fun initSearchView() {
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                adapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
//                adapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.productFlow.collect {
                when (it?.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.gone()
                        it.data?.let {
                                products -> adapter.addData(products)
                        }
                        binding.rvProducts.adapter=adapter
                    }

                    Status.ERROR -> {
                        binding.progressBar.gone()
                    }

                    Status.LOADING -> {
                        binding.progressBar.visible()
                    }

                    else -> {
                        binding.progressBar.gone()
                    }
                }
            }
        }
    }

}