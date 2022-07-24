package dev.marawanxmamdouh.shoestore.ui.shoedetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dev.marawanxmamdouh.shoestore.databinding.FragmentShoeDetailBinding
import dev.marawanxmamdouh.shoestore.ui.SharedViewModel


class ShoeDetail : Fragment() {

    private lateinit var binding: FragmentShoeDetailBinding
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeDetailBinding.inflate(inflater, container, false)
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        binding.btnSaveAddNewShoe.setOnClickListener {
            if (sharedViewModel.checkIfNotEmptyShoe()) {
                sharedViewModel.addShoe()
                it.findNavController()
                    .navigate(
                        ShoeDetailDirections.actionShoeDetailToShoeList(
                            sharedViewModel.shoe
                        )
                    )
            }else{
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnCancelAddNewShoe.setOnClickListener {
            it.findNavController()
                .navigate(ShoeDetailDirections.actionShoeDetailToShoeList(sharedViewModel.createEmptyShoe()))
        }

        binding.ivAddShoe.setOnClickListener {
            openGallery()
        }

        binding.sharedViewModel = sharedViewModel

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
