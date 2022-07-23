package dev.marawanxmamdouh.shoestore.ui.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dev.marawanxmamdouh.shoestore.databinding.FragmentInstructionsBinding
import dev.marawanxmamdouh.shoestore.models.Shoe

class InstructionsFragment : Fragment() {

    private lateinit var binding: FragmentInstructionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInstructionsBinding.inflate(inflater, container, false)

        binding.btnFinish.setOnClickListener {
            it.findNavController().navigate(
                InstructionsFragmentDirections.actionInstructionsFragmentToShoeList(
                    Shoe("",0.0,"","",0)
                )
            )
        }
        return binding.root
    }
}