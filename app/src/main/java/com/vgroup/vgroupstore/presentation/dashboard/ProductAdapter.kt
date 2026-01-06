package com.vgroup.vgroupstore.presentation.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vgroup.vgroupstore.databinding.ItemProductBinding
import com.vgroup.vgroupstore.domain.model.ProductList

class
ProductAdapter(
    private var items: List<ProductList>,
    private val onAddToCart: (ProductList) -> Unit,
    private val onItemClick: (ProductList) -> Unit = {}
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(
            ItemProductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            tvProductTitle.text = item.title
            tvProductPrice.text = "Price: $${item.variants?.firstOrNull()?.price?.toDouble()}"

            Glide.with(root.context)
                .load(item.images?.get(0)?.src)
                .into(ivProductImage)

            btnAddToCart.setOnClickListener { onAddToCart(item) }
            root.setOnClickListener { onItemClick(item) }

        }
    }

    override fun getItemCount() = items.size

    fun updateItems(newList: List<ProductList>) {
        items = newList
        notifyDataSetChanged()
    }

}