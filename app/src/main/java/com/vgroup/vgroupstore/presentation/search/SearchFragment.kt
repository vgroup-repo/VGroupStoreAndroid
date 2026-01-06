package com.vgroup.vgroupstore.presentation.search


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.vgroup.vgroupstore.databinding.FragmentSearchBinding
import com.vgroup.vgroupstore.domain.model.toProduct
import com.vgroup.vgroupstore.presentation.dashboard.ProductAdapter
import com.vgroup.vgroupstore.presentation.dashboard.SharedProductsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    // Shared ViewModel (products already loaded in Dashboard)
    private val viewModel: SharedProductsViewModel by activityViewModels()

    private lateinit var adapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()
        setupSearchBar()
        observeSearchResults()

        // ❌ Do NOT show data initially
        binding.rvProducts.visibility = View.GONE
        binding.tvCategory.visibility = View.GONE
    }


    // RECYCLER VIEW SETUP
    private fun setupRecyclerView() {
        adapter = ProductAdapter(
            items = emptyList(),

            onAddToCart = { product ->
                val product = product.toProduct()
                viewModel.addToCart(product)
                Toast.makeText(requireContext(), "Item Added to cart", Toast.LENGTH_LONG).show()

            },
            onItemClick = { }
        )

        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvProducts.adapter = adapter
    }

    // SEARCH BAR LISTENER
    private fun setupSearchBar() {
        binding.etDashboardSearch.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()

                if (query.isEmpty()) {

                    binding.rvProducts.visibility = View.GONE
                    binding.tvCategory.visibility = View.GONE
                    return
                }

                // Trigger filtering
                viewModel.performItemSearch(query)
            }
        })
    }

    // OBSERVE FILTERED SEARCH RESULTS ONLY
    private fun observeSearchResults() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchResults.collectLatest { results ->

                if (results.isEmpty()) {
                    binding.rvProducts.visibility = View.GONE
                    binding.tvCategory.visibility = View.VISIBLE
                    binding.tvCategory.text = "No results found"
                    return@collectLatest
                }

                // Show results only after filtering
                binding.rvProducts.visibility = View.VISIBLE
                binding.tvCategory.visibility = View.VISIBLE
                binding.tvCategory.text = "Search Results"

                adapter.updateItems(results)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


