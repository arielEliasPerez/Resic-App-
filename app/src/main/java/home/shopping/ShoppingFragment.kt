package home.shopping

import adapter.MyAdapter
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import com.example.resicapp.databinding.FragmentShoppingBinding
import data.Product
import data.OrderBy
import repositories.ProductRepository
import user.HistoryActivity
import user.UserActivity
import user.UserSystem

class ShoppingFragment : Fragment() {
    private lateinit var binding: FragmentShoppingBinding
    private lateinit var tvOrderBy : TextView
    private lateinit var tvuserHistory : TextView
    private lateinit var tvCash : TextView
    private val user = UserSystem.user

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentShoppingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shopping, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvOrderBy = view.findViewById(R.id.tv_pop_up)
        tvuserHistory = view.findViewById(R.id.tv_s_user)
        tvCash = view.findViewById(R.id.tv_s_cash)

        tvuserHistory.text = user!!.nickName
        tvCash.text = getString(R.string.user_cash , user.money.toString())

        val products = ProductRepository.get()

        showProducts(view , products)

        tvOrderBy.setOnClickListener{
            popUpMenu(view)
        }

        tvuserHistory.setOnClickListener{
            popUpMenuUserHistory(view)
        }
    }

    private fun popUpMenuUserHistory(view: View) {
        val menu = PopupMenu(requireContext() , tvuserHistory)

        menu.menuInflater.inflate(R.menu.menu_pop_up_user_history, menu.menu)

        menuSelectedUserHistory(menu , view)

        menu.show()
    }

    private fun menuSelectedUserHistory(menu: PopupMenu, view: View) {
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

    private fun showProducts(view: View, products: List<Product>) {
        val rv: RecyclerView = view.findViewById(R.id.rv_products)

        rv.layoutManager = LinearLayoutManager(requireContext())


        rv.adapter = MyAdapter(products.toList(), requireContext())
    }

    private fun popUpMenu(view: View) {
        val menu = PopupMenu(requireContext() , tvOrderBy)

        menu.menuInflater.inflate(R.menu.menu_pop_up_order_by , menu.menu)

        menuSelected( view , menu)

        menu.show()

    }

    private fun menuSelected(view: View, menu: PopupMenu) {
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.ascendant -> {
                    changeView(view , OrderBy.PRICE_ASCENDANT)
                    true
                }

                R.id.descendant -> {
                    changeView(view , OrderBy.PRICE_DESCENDANT)
                    true
                }
                R.id.topRated -> {
                    changeView(view , OrderBy.TOP_RATED)
                    true
                }
                R.id.lowRated -> {
                    changeView(view , OrderBy.LOW_RATED)
                    true
                }
                else -> false
            }
        }
    }

    private fun changeView(view: View, orderBy: OrderBy) {
        val products = productsOrderBy(i = orderBy)
        showProducts(view, products)
    }

    private fun productsOrderBy(i: OrderBy): List<Product> {
        val products = ProductRepository.get()

        val productsFiltered :  List<Product> = when (i) {
            OrderBy.PRICE_ASCENDANT -> products.sortedBy { it.price }

            OrderBy.PRICE_DESCENDANT-> products.sortedByDescending { it.price }

            OrderBy.TOP_RATED -> products.sortedByDescending { it.stars }

            OrderBy.LOW_RATED-> products.sortedBy { it.stars }

        }
        return productsFiltered
    }
}