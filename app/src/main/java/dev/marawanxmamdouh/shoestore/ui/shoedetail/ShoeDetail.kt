package dev.marawanxmamdouh.shoestore.ui.shoedetail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dev.marawanxmamdouh.shoestore.databinding.FragmentShoeDetailBinding


class ShoeDetail : Fragment() {

    private val PHOTO_PICKER_REQUEST_CODE = 3
    private lateinit var binding: FragmentShoeDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeDetailBinding.inflate(inflater, container, false)

        binding.btnCancelAddNewShoe.setOnClickListener {
            it.findNavController().navigate(ShoeDetailDirections.actionShoeDetailToShoeList())
        }

        binding.btnSaveAddNewShoe.setOnClickListener {
            it.findNavController().navigate(ShoeDetailDirections.actionShoeDetailToShoeList())
        }

        binding.ivAddShoe.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            intent.type = "image/*"
            startActivityForResult(intent, PHOTO_PICKER_REQUEST_CODE)
        }
        return binding.root
    }

    // onActivityResult() handles callbacks from the photo picker.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(context, "404", Toast.LENGTH_SHORT).show()
            return
        }

        if (data != null) {
            binding.ivAddShoe.setImageURI(data.data)
        }
        return
    }
}
