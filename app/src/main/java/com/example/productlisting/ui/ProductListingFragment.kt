package com.example.productlisting.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.productlisting.databinding.FragmentProductListingBinding

class ProductListingFragment : Fragment() {
    private lateinit var binding: FragmentProductListingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding= FragmentProductListingBinding.inflate(layoutInflater)
        return binding.root
    }
}