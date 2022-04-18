package com.example.resale

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.resale.databinding.FragmentCatalogBinding
import com.example.resale.models.Item
import com.example.resale.models.ItemAdapter
import com.example.resale.models.ItemViewModel

class CatalogFragment : Fragment() {

    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!

    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemViewModel.itemsLiveData.observe(viewLifecycleOwner) { rawItems ->
            binding.recyclerView.visibility = if(rawItems == null) View.GONE else View.VISIBLE
            if(rawItems != null) {
                var items: List<Item> = filterAndSort(rawItems)

                val adapter = ItemAdapter(items) { position ->
                    val pictureUrl: String = if(items[position].pictureUrl == null) ""
                        else items[position].pictureUrl!!
                    val action = CatalogFragmentDirections.actionCatalogFragmentToItemFragment(
                        items[position].id,
                        items[position].title,
                        items[position].description,
                        items[position].price,
                        items[position].seller,
                        items[position].date,
                        pictureUrl)
                    findNavController().navigate(action)
                }
                binding.recyclerView.layoutManager = LinearLayoutManager(activity)
                binding.recyclerView.adapter = adapter

            }
        }

        itemViewModel.errorMessageLiveData.observe(viewLifecycleOwner) { errorMessage ->
            binding.textViewCatalogError.text = errorMessage
            binding.textViewCatalogError.visibility = View.VISIBLE
        }

        itemViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            itemViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }
    }

    fun filterAndSort(rawItems: List<Item>) : List<Item> {
        val filteredItems: List<Item> = filterItems(rawItems)
        return sortItems(filteredItems)
    }
    fun filterItems(input: List<Item>) : List<Item> {
        val searchTerm: String = binding.editTextCatalogFilter.text.toString()
        return input.filter { item ->
            if(item.title.contains(searchTerm) || item.description.contains(searchTerm)) {
                return@filter true
            }
            return@filter false
        }
    }
    fun sortItems(input: List<Item>) : List<Item> {
        if(binding.radioCatalogPrice.isChecked) {
            if(binding.radioCatalogAscending.isChecked) {
                return input.sortedBy { item ->
                    return@sortedBy item.price
                }
            } else {
                return input.sortedByDescending { item ->
                    return@sortedByDescending item.price
                }
            }
        } else {
            if(binding.radioCatalogAscending.isChecked) {
                return input.sortedBy { item ->
                    return@sortedBy item.date
                }
            } else {
                return input.sortedByDescending { item ->
                    return@sortedByDescending item.date
                }
            }
        }
        return input // Should not ever happen, but this is ensured in the layout file, so the compiler obviously doesn't understand.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}