package com.example.productlisting.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.productlisting.R
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
    private var productsList:ArrayList<Product> = arrayListOf()

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
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.addProductFab.setOnClickListener {
            findNavController().navigate(R.id.action_productListingFragment_to_addProductFragment)
        }
    }

    private fun initSearchView() {
        binding.searchView.clearFocus()
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
              filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                filter(newText)
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
                            productsList.addAll(products)
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

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredList: ArrayList<Product> = ArrayList()

        // running a for loop to compare elements.
        for (item in productsList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.productName?.toLowerCase()?.contains(text.toLowerCase()) == true) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(requireContext(), "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.addData(filteredList)
        }
    }

}