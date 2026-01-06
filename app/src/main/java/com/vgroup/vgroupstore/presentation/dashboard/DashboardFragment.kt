package com.vgroup.vgroupstore.presentation.dashboard



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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.vgroup.vgroupstore.core.AuthPrefs
import com.vgroup.vgroupstore.databinding.FragmentDashboardBinding
import com.vgroup.vgroupstore.domain.model.toProduct
import com.vgroup.vgroupstore.presentation.cart.CartManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var cartManager: CartManager
    @Inject
    lateinit var prefs: AuthPrefs
    private val args: DashboardFragmentArgs by navArgs()
    private val sharedViewModel: SharedProductsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupRecyclerView()
        setupClickListeners()
        observeUiState()

        sharedViewModel.loadProducts()
    }

    // RECYCLER VIEW SETUP
    private fun setupRecyclerView() {
        binding.rvProducts.layoutManager = GridLayoutManager(requireContext(), 2)
    }


    // CLICK LISTENERS (Search + Cart buttons)
      private fun setupClickListeners() {

        binding.etDashboardSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString().trim()
                sharedViewModel.performSearch(query)
            }

            override fun afterTextChanged(s: Editable?) {}
        })

    }


    // OBSERVE UI STATE FROM VIEWMODEL
    private fun observeUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.state.collectLatest { state ->
                when (state) {

                    is DashboardUiState.Loading -> {
//                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is DashboardUiState.Success -> {
//                        binding.progressBar.visibility = View.GONE

                        val adapter = ProductAdapter(
                            items = state.products,
                            onAddToCart = { product ->
                                val product = product.toProduct()
                                sharedViewModel.addToCart(product)
                                Toast.makeText(requireContext(), "Item Added to cart", Toast.LENGTH_LONG).show()
                            },
                            onItemClick = { product ->

                            }
                        )

                        binding.rvProducts.adapter = adapter
                    }

                    is DashboardUiState.Error -> {
//                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
