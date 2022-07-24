package dev.marawanxmamdouh.shoestore.ui.shoelist

import android.content.res.Resources
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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

        sharedViewModel.shoeList.observe(viewLifecycleOwner) {
            // Update the UI with the new shoe data from the arguments
            addNewShoeToLayout(it)
        }

        binding.fab.setOnClickListener {
            it.findNavController().navigate(ShoeListDirections.actionShoeListToShoeDetail())
            sharedViewModel.createEmptyShoe()
        }

        return binding.root
    }

    private fun addNewShoeToLayout(shoeList: MutableList<Shoe>) {
        for (shoe in shoeList) {
            val layout = createLinearLayout()
            layout.addView(createImageView())
            layout.addView(createTextView(shoe.name))
            layout.addView(createTextView(shoe.size.toString()))
            layout.addView(createTextView(shoe.company))
            layout.addView(createTextView(shoe.description))
            binding.llShoeList.addView(layout)
        }
    }

    private fun createImageView(): ImageView {
        val imageView = ImageView(this.context)
        val layoutParams = LinearLayout.LayoutParams(150.px, 150.px)
        imageView.layoutParams = layoutParams
        imageView.setImageResource(R.drawable.shoe)
        return imageView
    }

    private fun createLinearLayout(): LinearLayout {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            weight = 1.0f
            gravity = Gravity.CENTER_HORIZONTAL
        }
        layout.layoutParams = params
        layout.setPadding(0, 16.px, 0, 16.px)
        return layout
    }

    private fun createTextView(text: String): TextView {
        val tv = TextView(this.context)
        tv.text = text
        tv.textSize = 20.0f
        tv.setPadding(0, 8.px, 0, 8.px)
        tv.gravity = Gravity.CENTER_HORIZONTAL
        return tv
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
                        sharedViewModel.clearShoeList()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    // convert dp to px
    private val Int.px get() = (this * Resources.getSystem().displayMetrics.density).toInt()

}