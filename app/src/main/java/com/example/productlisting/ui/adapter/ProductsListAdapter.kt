package com.example.productlisting.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.productlisting.data.model.Product
import com.example.productlisting.databinding.ItemProductBinding


class ProductsListAdapter(
) : RecyclerView.Adapter<ProductsListAdapter.DataViewHolder>(){

    private var products:ArrayList<Product> = arrayListOf()
    private var filteredProductList:ArrayList<Product> = arrayListOf()

    class DataViewHolder(private val binding:ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            binding.apply {
                ivProduct.load(product.image)
                tvProductName.text=product.productName
                tvProductType.text=product.productType
                tvPriceValue.text=product.price.toString()
                tvTaxValue.text=product.tax.toString()
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
        filteredProductList=products
        notifyDataSetChanged()
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charString = constraint?.toString() ?: ""
//                filteredProductList = if (charString.isEmpty()) products else {
//                    val filteredList = ArrayList<Product>()
//                    products
//                        .filter {
//                            (it.productName?.contains(constraint!!) == true) ||
//                                    (constraint?.let { it1 -> it.productType?.contains(it1) } == true)
//
//                        }
//                        .forEach { filteredList.add(it) }
//                    filteredList
//
//                }
//                return FilterResults().apply { values = filteredProductList }
//            }
//
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//
//                filteredProductList = if (results?.values == null)
//                    arrayListOf()
//                else
//                    results.values as ArrayList<Product>
//                notifyDataSetChanged()
//            }
//        }
//    }

}