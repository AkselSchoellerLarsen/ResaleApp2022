package com.example.resale

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.resale.databinding.DialogCreateItemBinding
import com.example.resale.models.Item
import com.example.resale.models.ItemViewModel
import com.example.resale.util.DateConverter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.*

class CreateItemDialogFragment : DialogFragment() {

    private var _binding: DialogCreateItemBinding? = null
    private val binding get() = _binding!!

    private val itemViewModel: ItemViewModel by viewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogCreateItemBinding.inflate(LayoutInflater.from(context))

        return AlertDialog.Builder(requireActivity())
            .setView(binding.root)
            .setPositiveButton(R.string.create_item
            ) { _, _ ->
                val title = binding.editTextCreateTitle.text.toString().trim()
                val description = binding.editTextCreateDescription.text.toString().trim()
                val textPrice = binding.editTextCreatePrice.text.toString().trim()
                val price = if (textPrice.toIntOrNull() == null) 0 else textPrice.toInt()
                val seller =
                    Firebase.auth.currentUser!!.email.toString() //Can only enter dialog when signed in
                val date = DateConverter.javaDateToUnixDate(Date())
                val pictureUrl = binding.editTextCreatePictureUrl.text.toString().trim()

                val itemToAdd = Item(-1, title, description, price, seller, date, pictureUrl)
                Log.d("TagNotUsedByOthers", "Trying to create: $itemToAdd")
                itemViewModel.add(itemToAdd)
            }
            .setNegativeButton(R.string.cancel
            ) { _, _ ->
                dialog?.cancel()
            }
            .create()
    }
}