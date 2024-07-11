package home.search

import adapter.CategoryAdapter
import adapter.MyAdapter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import com.example.resicapp.databinding.FragmentSearchBinding
import data.Category
import data.Product
import data.ProductType
import repositories.CategoryRepository
import repositories.ProductRepository
import user.UserSystem

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var reset: ImageButton
    private lateinit var tvLabelType: TextView
    private lateinit var tvMessage: TextView
    private lateinit var search: SearchView
    private lateinit var rv: RecyclerView

    private val userProduct = UserSystem.userProducts()
    private val category = categoryPreferences()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv      = view.findViewById(R.id.rv_category_and_products)
        reset = view.findViewById(R.id.btn_back_search)
        search  = view.findViewById(R.id.searchView)
        tvLabelType = view.findViewById(R.id.tv_pop_up)
        tvMessage = view.findViewById(R.id.tv_message_search)



        showCategory(view = view, categories = category)

        searchProducts()

        tvLabelType.setOnClickListener{
            popUpMenu(view)
        }

        reset.setOnClickListener{
          reset(view)
        }

    }

    private fun reset(view: View) {
        rv.visibility = View.VISIBLE
        reset.visibility = View.INVISIBLE
        tvLabelType.visibility = View.VISIBLE
        tvMessage.visibility = View.INVISIBLE
        showCategory(view = view, categories = category)
    }

    private fun searchProducts() {
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    resetView()
                    showResult(it)

                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // No hacer nada en este método
                return false
            }
        })
    }

    private fun resetView() {
        tvLabelType.visibility = View.GONE
        reset.visibility = View.VISIBLE
        rv.visibility = View.VISIBLE
        tvMessage.visibility = View.VISIBLE
    }

    fun showResult(search: String){
        val products = ProductRepository.get().filter {
            it.name.contains(search, ignoreCase = true) || it.author.contains(
                search,
                ignoreCase = true
            )
        }

        if(products.isEmpty()) notFound(search) else found(search  , products)

    }

    private fun found(search: String, products: List<Product>) {
        tvMessage.text = getString(R.string.found , search)

        rv.layoutManager = LinearLayoutManager(requireContext())

        rv.adapter = MyAdapter(products.toList(), requireContext())


    }

    private fun notFound(search: String) {
        rv.visibility = View.INVISIBLE
        tvMessage.text =  getString(R.string.products_not_found , search)
    }

    private fun popUpMenu(view: View) {
        val menu = PopupMenu(requireContext() , tvLabelType)

        menu.menuInflater.inflate(R.menu.menu_pop_up_type_category, menu.menu)

        menuSelected(menu , view)

        menu.show()
    }

    private fun menuSelected(menu: PopupMenu, view: View) {
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_p_disc -> {
                    val categories = CategoryRepository.getMusicCategories()
                    showCategory(view, categories)
                    true
                }

                R.id.menu_p_books -> {
                    val categories =  CategoryRepository.getBookCategories()
                    showCategory(view, categories)
                    true
                }
                else -> false
            }
        }
    }

    private fun showCategory(view: View, categories: List<Category>) {
        val rv : RecyclerView = view.findViewById(R.id.rv_category_and_products)

        rv.layoutManager = GridLayoutManager(requireContext() , 2)

        rv.adapter = CategoryAdapter(categories.toList() , requireContext())
    }


    private fun categoryPreferences(): List<Category> {
        val productsBook = 0

        return when (comparator()) {
            productsBook -> CategoryRepository.getBookCategories()

            else -> CategoryRepository.getMusicCategories()
        }
    }

    private fun comparator() : Int{
        val countTypeDisc = userProduct.filter { it.type == ProductType.DISC }.size

        val countTypeBook = userProduct.filter { it.type == ProductType.BOOK }.size

        if (countTypeBook > countTypeDisc) return 0

        return 1
    }

}