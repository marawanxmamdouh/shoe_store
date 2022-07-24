package dev.marawanxmamdouh.shoestore.ui.shoedetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dev.marawanxmamdouh.shoestore.databinding.FragmentShoeDetailBinding
import dev.marawanxmamdouh.shoestore.ui.SharedViewModel


class ShoeDetail : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private lateinit var viewModel: ShoeDetailViewModel
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeDetailBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ShoeDetailViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        binding.btnSaveAddNewShoe.setOnClickListener {
            it.findNavController()
                .navigate(
                    ShoeDetailDirections.actionShoeDetailToShoeList(
                        crateNewShoe()
                    )
                )
        }

        binding.btnCancelAddNewShoe.setOnClickListener {
            it.findNavController()
                .navigate(ShoeDetailDirections.actionShoeDetailToShoeList(Shoe("", 0.0, "", "", 0)))
        }

        binding.ivAddShoe.setOnClickListener {
            openGallery()
        }

        binding.viewModel = viewModel
        binding.sharedViewModel = sharedViewModel

        return binding.root
    }

    private fun crateNewShoe(): Shoe {
        val shoe = Shoe(
            binding.etShoeName.text.toString(),
            parseDouble(binding.etShoeSize.text.toString()),
            binding.etCompanyName.text.toString(),
            binding.etShoeDescription.text.toString(),
            binding.ivAddShoe.tag as? Int ?: 0,
        )
        sharedViewModel.addShoe(shoe)
        return shoe
    }

    private fun parseDouble(s: String?): Double {
        return if (s == null || s.isEmpty()) 0.0 else s.toDouble()
    }

    // open gallery to select image
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }

    // get image from gallery and set it to image view
    private var resultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                binding.ivAddShoe.setImageURI(data?.data)
            }
        }
}
