package com.vgroup.vgroupstore.presentation.cart

import com.vgroup.vgroupstore.presentation.adapter.CartAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.vgroup.vgroupstore.data.entities.CartEntity
import com.vgroup.vgroupstore.databinding.FragmentCartBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels()

    private lateinit var cartAdapter: CartAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()
        observeCart()

        // Load items
        viewModel.loadCart()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            onIncrease = { item -> viewModel.increase(item) },
            onDecrease = { item -> viewModel.decrease(item) },
            onDelete = { item -> viewModel.remove(item) }
        )

        binding.rvCartItems.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = cartAdapter
        }
    }

    private fun observeCart() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.cart.collectLatest { cartItems ->
                cartAdapter.submitList(cartItems)
                updateSummary(cartItems)
            }
        }
    }

    private fun updateSummary(items: List<CartEntity>) {
        val subtotal = items.sumOf { it.price * it.quantity }
        val shipping = 4.99.takeIf { subtotal > 0.0 } ?: 0.0
        val total = subtotal + shipping

        binding.tvSubtotal.text = "$" + String.format("%.2f", subtotal)
        binding.tvShipping.text = "$" + String.format("%.2f", shipping)
        binding.tvTotal.text = "$" + String.format("%.2f", total)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
