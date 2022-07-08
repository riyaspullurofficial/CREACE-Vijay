package com.riyas.creacevijay.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.riyas.creacevijay.R
import com.riyas.creacevijay.databinding.ProductListCardBinding
import com.riyas.creacevijay.db.Product

class ProductRecyclerViewAdapter (private val productList: List<Product>,val clickListener: ClickListener):ListAdapter<Product,MyViewHolder>(ProductDiffUtil()) {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       var layoutInflater=LayoutInflater.from(parent.context)
        val binding:ProductListCardBinding=DataBindingUtil.inflate(layoutInflater,R.layout.product_list_card,parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(productList[position])
        holder.itemView.setOnClickListener {
                clickListener.onItemClick(productList[position])
        }



    }

    override fun getItemCount(): Int = productList.size

    interface ClickListener{
        fun onItemClick(product: Product)
    }


}


class MyViewHolder(private val binding:ProductListCardBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(product: Product){
            binding.productNameCardRV.text=product.product
            binding.productIDCardRV.text=product.id.toString()
        }
}
class ProductDiffUtil: DiffUtil.ItemCallback<Product>(){
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }


}