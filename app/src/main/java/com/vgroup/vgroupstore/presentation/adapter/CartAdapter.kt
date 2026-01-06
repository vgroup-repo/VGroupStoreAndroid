package com.vgroup.vgroupstore.presentation.adapter



import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vgroup.vgroupstore.data.entities.CartEntity
import com.vgroup.vgroupstore.databinding.ItemCartBinding


class CartAdapter(
    private val onIncrease: (CartEntity) -> Unit,
    private val onDecrease: (CartEntity) -> Unit,
    private val onDelete: (CartEntity) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    private var items: List<CartEntity> = emptyList()

    fun submitList(newList: List<CartEntity>) {
        items = newList
        notifyDataSetChanged()
    }

    inner class CartViewHolder(val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartEntity) {
            binding.tvTitle.text = item.title
            binding.tvPrice.text = "$" + item.price
            binding.tvQuantity.text = item.quantity.toString()

            Glide.with(binding.ivProduct.context)
                .load(item.image)
                .into(binding.ivProduct)

            binding.btnPlus.setOnClickListener { onIncrease(item) }
            binding.btnMinus.setOnClickListener { onDecrease(item) }
            binding.btnDelete.setOnClickListener { onDelete(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CartViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }
}

