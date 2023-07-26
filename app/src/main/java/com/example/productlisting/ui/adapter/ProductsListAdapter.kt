package com.example.productlisting.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.productlisting.data.model.Product
import com.example.productlisting.databinding.ItemProductBinding


class ProductsListAdapter(
) : RecyclerView.Adapter<ProductsListAdapter.DataViewHolder>() {

    private var products: ArrayList<Product> = arrayListOf()
    private var filteredProductList: ArrayList<Product> = arrayListOf()

    class DataViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                ivProduct.load(if (product.image?.isNotEmpty() == true) product.image else "https://lukaszmazurkiewicz.com/wp-content/uploads/2019/08/perfume-bottle-calvin-klein-product-photography.jpg")
                tvProductName.text = product.productName
                tvProductType.text = product.productType
                tvPriceValue.text = product.price.toString()
                tvTaxValue.text = product.tax.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder {
        val binding = ItemProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return DataViewHolder(binding)
    }

    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(products[position])

    fun addData(productsList: List<Product>) {
        products.clear()
        products.addAll(productsList)
        filteredProductList = products
        notifyDataSetChanged()
    }
}