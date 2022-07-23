package dev.marawanxmamdouh.shoestore.ui.shoelist

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dev.marawanxmamdouh.shoestore.R
import dev.marawanxmamdouh.shoestore.databinding.FragmentShoeListBinding
import dev.marawanxmamdouh.shoestore.models.Shoe
import dev.marawanxmamdouh.shoestore.ui.SharedViewModel


class ShoeList : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private lateinit var viewModel: ShoeListViewModel
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ShoeListViewModel::class.java]
        sharedViewModel = ViewModelProvider(requireActivity())[SharedViewModel::class.java]

        // get the arguments from ShoeDetailFragment
        val args = ShoeListArgs.fromBundle(requireArguments())
        Toast.makeText(
            context,
            "shoeName = ${args.shoe.name}, company = ${args.shoe.company}, size = ${args.shoe.size}, description = ${args.shoe.description}",
            Toast.LENGTH_SHORT
        ).show()

        sharedViewModel.shoeList.observe(viewLifecycleOwner) {
            // Update the UI with the new shoe data from the arguments
            addNewShoeToLayout(it)
        }

        binding.fab.setOnClickListener {
            it.findNavController().navigate(ShoeListDirections.actionShoeListToShoeDetail())
        }

        return binding.root
    }

    private fun addNewShoeToLayout(shoeList: MutableList<Shoe>) {
        for (shoe in shoeList) {
            val layout = LinearLayout(context)
            layout.orientation = LinearLayout.VERTICAL
            val imageView = ImageView(this.context)
            val layoutParams = LinearLayout.LayoutParams(1000, 1000)
            imageView.layoutParams = layoutParams
            imageView.setImageResource(R.drawable.shoe)
            val tvShoeName = TextView(this.context)
            tvShoeName.text = shoe.name
            val tvCompany = TextView(this.context)
            tvCompany.text = shoe.company
            val tvSize = TextView(this.context)
            tvSize.text = shoe.size.toString()
            val tvDescription = TextView(this.context)
            tvDescription.text = shoe.description
            layout.addView(imageView)
            layout.addView(tvShoeName)
            layout.addView(tvCompany)
            layout.addView(tvSize)
            layout.addView(tvDescription)
            binding.llShoeList.addView(layout)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.overflow_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                // Handle the menu selection
                return when (menuItem.itemId) {
                    R.id.miLogout -> {
                        view.findNavController().navigate(R.id.loginFragment)
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}