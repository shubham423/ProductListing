package com.example.productlisting.ui.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.productlisting.R
import com.example.productlisting.data.model.AddProductRequest
import com.example.productlisting.databinding.FragmentAddProductBinding
import com.example.productlisting.ui.viewmodels.AddProductViewModel
import com.example.productlisting.utils.Status
import com.example.productlisting.utils.gone
import com.example.productlisting.utils.visible
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream

class AddProductFragment : Fragment() {
    private lateinit var binding: FragmentAddProductBinding
    private val viewModel: AddProductViewModel by viewModel()
    private var productImageUri:Uri?=null

    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // Handle the returned Uri
        if (uri != null) {
            productImageUri=uri
            binding.ivProduct.setImageURI(uri)
        } else {
            Toast.makeText(requireActivity(),
                getString(R.string.no_image_was_chosen), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddProductBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val items = listOf(
            "Food", "Grocery", "SmartPhone", "Laptop", "Shoes"
        )

        //populate product type spinner
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_list_item, items)
        (binding.productTypeLayout.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        initClickListeners()
        initObservers()
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.productFlow.collect {
                when (it?.status) {
                    Status.SUCCESS -> {
                        binding.progressBar.gone()
                        if (it.data?.success==true){
                            Toast.makeText(requireContext(),
                                getString(R.string.product_added_succesfully), Toast.LENGTH_SHORT).show()
                        }
                    }

                    Status.ERROR -> {
                        binding.progressBar.gone()
                        Toast.makeText(requireContext(),
                            getString(R.string.some_thing_went_wrong, it.message), Toast.LENGTH_SHORT).show()
                    }

                    Status.LOADING -> {
                        binding.progressBar.visible()
                    }

                    else -> {}
                }
            }
        }
    }


    private fun initClickListeners() {
        binding.selectImagesButton.setOnClickListener {
            getContent.launch("image/*")
        }
        binding.submitButton.setOnClickListener {
            if (checkIfFieldsValid()){
                viewModel.addProduct(
                    AddProductRequest(
                        binding.edtProductType.text.toString(),
                        binding.productNameLayout.editText?.text.toString(),
                        binding.sellingPriceLayout.editText?.text.toString().toDouble(),
                        binding.taxRateLayout.editText?.text.toString().toDouble(),
                        productImageUri?.let { it1 -> uriToFile(it1,"image") }
                    )
                )
            }
        }
    }

    //Converts uri to File as server requires File not Uri
    private  fun uriToFile(uri: Uri, filename: String): File? {
        try {
            requireContext().contentResolver.openInputStream(uri)?.use { inputStream ->
                val file = File(requireContext().cacheDir, filename)
                FileOutputStream(file).use { outputStream ->
                    inputStream.copyTo(outputStream)
                    outputStream.flush()
                    return file
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }


    private fun checkIfFieldsValid(): Boolean {
        if (binding.edtProductType.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.please_fill_product_type), Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.productName.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.please_fill_product_name), Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.sellingPrice.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.please_fill_selling_price), Toast.LENGTH_SHORT).show()
            return false
        } else if (binding.taxRate.text.toString().trim().isEmpty()) {
            Toast.makeText(requireContext(),
                getString(R.string.please_fill_tax_rate), Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

}