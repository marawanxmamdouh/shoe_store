package dev.marawanxmamdouh.shoestore.ui.shoelist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import dev.marawanxmamdouh.shoestore.R
import dev.marawanxmamdouh.shoestore.databinding.FragmentShoeListBinding

class ShoeList : Fragment() {

    private lateinit var binding: FragmentShoeListBinding
    private lateinit var viewModel: ShoeListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShoeListBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[ShoeListViewModel::class.java]

        // get the arguments from ShoeDetailFragment
        val args = ShoeListArgs.fromBundle(requireArguments())
        Toast.makeText(context, "shoeName = ${args.shoeName}, company = ${args.company}, size = ${args.size}, description = ${args.description}", Toast.LENGTH_SHORT).show()

        binding.fab.setOnClickListener {
            it.findNavController().navigate(ShoeListDirections.actionShoeListToShoeDetail())
        }

        return binding.root
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