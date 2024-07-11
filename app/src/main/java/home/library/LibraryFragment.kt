package home.library

import adapter.UserAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import com.example.resicapp.databinding.FragmentLibraryBinding
import data.ProductType
import data.Purchase
import user.UserSystem

class LibraryFragment : Fragment() {
    private lateinit var binding:FragmentLibraryBinding
    private lateinit var tvType : TextView
    private lateinit var tvIsEmpty : TextView

    private val userPurchase = UserSystem.userPurchases()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentLibraryBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvIsEmpty = view.findViewById(R.id.tv_empty)
        tvType = view.findViewById(R.id.tv_pop_up)

        tvIsEmpty.visibility = View.GONE

        showProducts(view , userPurchase)

        tvType.setOnClickListener{
            popUpMenu(view)
        }
    }

    private fun popUpMenu(view: View) {
        val menu = PopupMenu(requireContext() , tvType)

        menu.menuInflater.inflate(R.menu.menu_pop_up_type_products, menu.menu)

        menuSelected(menu , view)

        menu.show()
    }

    private fun menuSelected(menu: PopupMenu, view: View) {
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_p_disc -> {
                    tvType.text = getString(R.string.disc)
                    changeView(view , ProductType.DISC)
                    true
                }

                R.id.menu_p_books -> {
                    tvType.text = getString(R.string.book)
                    changeView(view , ProductType.BOOK)
                    true
                }

                R.id.menu_p_alls -> {
                    tvType.text = getString(R.string.all)
                    showProducts(view , userPurchase)
                    true
                }
                else -> false
            }
        }
    }

    private fun changeView(view: View, type: ProductType) {
        val purchaseType = UserSystem.purchaseType(type)

        showProducts(view, purchaseType)
    }

    private fun showProducts(view: View , purchases: List<Purchase>) {

        val rv: RecyclerView = view.findViewById(R.id.rv_userProducts)

        rv.layoutManager = LinearLayoutManager(requireContext())

        rv.adapter = UserAdapter(purchases.toList(), requireContext())

        if(purchases.isEmpty()){
            tvIsEmpty.visibility = View.VISIBLE
            rv.visibility = View.INVISIBLE
        }
    }


}