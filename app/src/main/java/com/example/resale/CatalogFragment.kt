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

        itemViewModel.itemsLiveData.observe(viewLifecycleOwner) { items ->
            binding.recyclerView.visibility = if(items == null) View.GONE else View.VISIBLE
            if(items != null) {
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
        }

        itemViewModel.reload()

        binding.swiperefresh.setOnRefreshListener {
            itemViewModel.reload()
            binding.swiperefresh.isRefreshing = false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}