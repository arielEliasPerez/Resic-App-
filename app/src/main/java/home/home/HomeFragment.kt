package home.home

import adapter.MyAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import com.example.resicapp.databinding.FragmentHomeBinding
import user.HistoryActivity
import user.UserActivity
import user.UserSystem

class HomeFragment : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var imButtonUserOrHistory: ImageButton
    private lateinit var toolbar: Toolbar

    private val userProduct = UserSystem.userProducts()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imButtonUserOrHistory = view.findViewById(R.id.imb_user_history)

        showProducts(view)

        imButtonUserOrHistory.setOnClickListener{
            popUpMenu(view)
        }

        changeTheme(view)
    }

    private fun changeTheme(view: View) {
        toolbar = view.findViewById(R.id.toolbar_home)
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)

        // Añadir el menú utilizando el MenuProvider
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_theme, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.dayMode -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        true
                    }

                    R.id.darkMode -> {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        true
                    }

                    else -> false
                }
            }
        }, viewLifecycleOwner)
    }


    private fun popUpMenu(view: View) {
        val menu = PopupMenu(requireContext() , imButtonUserOrHistory)

        menu.menuInflater.inflate(R.menu.menu_pop_up_user_history, menu.menu)

        menuSelected(menu , view)

        menu.show()
    }


    private fun menuSelected(menu: PopupMenu, view: View) {
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_action_user -> {
                    val intent = Intent(requireContext(), UserActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.menu_action_history -> {
                    val intent = Intent(requireContext(), HistoryActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }
    
    private fun showProducts(view: View) {
        val rv : RecyclerView = view.findViewById(R.id.rv_products)

        rv.layoutManager = LinearLayoutManager(requireContext())

        val preferredProducts = UserSystem.getPreferredProducts()

        rv.adapter = MyAdapter(preferredProducts.toList(), requireContext())
    }


}