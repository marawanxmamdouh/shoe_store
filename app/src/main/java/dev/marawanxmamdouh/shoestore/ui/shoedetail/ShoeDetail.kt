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
import androidx.navigation.findNavController
import dev.marawanxmamdouh.shoestore.databinding.FragmentShoeDetailBinding


class ShoeDetail : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeDetailBinding.inflate(inflater, container, false)

        binding.btnCancelAddNewShoe.setOnClickListener {
            it.findNavController()
                .navigate(
                    ShoeDetailDirections.actionShoeDetailToShoeList(
                        binding.etShoeName.text.toString(),
                        binding.etCompanyName.text.toString(),
                        binding.etShoeSize.text.toString(),
                        binding.etShoeDescription.text.toString()
                    )
                )
        }

        binding.btnSaveAddNewShoe.setOnClickListener {
            it.findNavController()
                .navigate(ShoeDetailDirections.actionShoeDetailToShoeList("", "", "", ""))
        }

        binding.ivAddShoe.setOnClickListener {
            openGallery()
        }

        return binding.root
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
