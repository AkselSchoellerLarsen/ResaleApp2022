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
import com.example.resale.databinding.FragmentItemBinding
import com.example.resale.models.Adapter
import com.example.resale.models.Item
import com.example.resale.models.ItemViewModel
import com.example.resale.util.DateConverter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ItemFragment : Fragment() {

    private var _binding: FragmentItemBinding? = null
    private val binding get() = _binding!!

    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = requireArguments()
        val itemFragmentArgs: ItemFragmentArgs = ItemFragmentArgs.fromBundle(bundle)

        var item = Item(itemFragmentArgs.itemId, itemFragmentArgs.itemTitle,
        itemFragmentArgs.itemDescription, itemFragmentArgs.itemPrice,
        itemFragmentArgs.itemSeller, itemFragmentArgs.itemDate, itemFragmentArgs.itemPictureUrl)

        binding.editTextTitle.setText(item.title)
        binding.editTextDescription.setText(item.description)
        binding.editTextPrice.setText(item.price.toString())
        binding.textViewSeller.setText(item.seller)
        binding.textViewDate.setText(DateConverter.unixDateToJavaDate(item.date).toString())
        binding.textViewHiddenDate.setText(item.date.toString())
        binding.editTextPictureUrl.setText(item.pictureUrl)

        binding.buttonItemDelete.setVisibility(isOwner(item.seller))
        binding.buttonItemUpdate.setVisibility(isOwner(item.seller))

        binding.buttonItemDelete.setOnClickListener {
            itemViewModel.delete(item.id)
            findNavController().popBackStack()
        }

        binding.buttonItemUpdate.setOnClickListener {
            val title = binding.editTextTitle.text.toString().trim()
            val description = binding.editTextDescription.text.toString().trim()
            val price = binding.editTextPrice.text.toString().trim().toInt()
            val seller = binding.textViewSeller.text.toString().trim()
            val date = binding.textViewHiddenDate.text.toString().trim().toInt()
            val pictureUrl = binding.editTextPictureUrl.text.toString().trim()

            val updatedItem = Item(item.id, title, description, price, seller, date, pictureUrl)
            itemViewModel.update(updatedItem)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isOwner(owner: String) : Int {
        if(Firebase.auth.currentUser?.email == owner) {
            return View.VISIBLE
        }
        return View.GONE
    }
}