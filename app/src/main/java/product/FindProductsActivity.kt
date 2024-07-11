package product

import adapter.MyAdapter
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import data.OrderBy
import com.example.resicapp.R
import data.Product
import repositories.ProductRepository

class FindProductsActivity : AppCompatActivity() {
    private lateinit var tvOrderBy : TextView
    private lateinit var tvCategory : TextView
    private lateinit var imbBack : ImageButton
    private var products = emptyList<Product>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_found_products)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imbBack = findViewById(R.id.btn_back)
        tvCategory = findViewById(R.id.tv_label)
        tvOrderBy = findViewById(R.id.tv_pop_up)


        init()

        tvOrderBy.setOnClickListener{
            popUpMenu()
        }

        imbBack.setOnClickListener{
            finish()
        }
    }

    private fun init() {
        val nameCategory = intent.getStringExtra("NameCategory")

        this.products = productsInACategory(nameCategory)

        tvCategory.text = products[0].category

        if(products.isNotEmpty()) showProducts(products)
    }

    private fun showProducts(product : List<Product>) {
        val rv: RecyclerView = findViewById(R.id.rv_products)

        rv.layoutManager = LinearLayoutManager(this)

        rv.adapter = MyAdapter(product.toList(), this )
    }

    private fun popUpMenu() {
        val menu = PopupMenu(this , tvOrderBy)

        menu.menuInflater.inflate(R.menu.menu_pop_up_order_by , menu.menu)

        menuSelected(menu)

        menu.show()

    }

    private fun menuSelected(menu: PopupMenu) {
        menu.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.ascendant -> {
                    changedView(OrderBy.PRICE_ASCENDANT)
                    true
                }

                R.id.descendant -> {
                    changedView(OrderBy.PRICE_DESCENDANT)
                    true
                }

                R.id.topRated -> {
                    changedView(OrderBy.TOP_RATED)
                    true
                }
                R.id.lowRated -> {
                    changedView(OrderBy.LOW_RATED)
                    true
                }

                else -> false
            }
        }
    }

    private fun changedView(orderBy: OrderBy) {
        val products = productsOrderBy(i = orderBy)
        showProducts(products)
    }

    private fun productsOrderBy(i: OrderBy): List<Product> {
        val productsFiltered :  List<Product> = when (i) {
            OrderBy.PRICE_ASCENDANT -> this.products.sortedBy { it.price }

            OrderBy.PRICE_DESCENDANT -> this.products.sortedByDescending { it.price }

            OrderBy.TOP_RATED-> this.products.sortedBy { it.stars }

            OrderBy.LOW_RATED -> this.products.sortedByDescending { it.stars }

        }
        return productsFiltered
    }

    private fun productsInACategory(nameCategory: String?): List<Product> {
        val product = ProductRepository.get()

        return product.filter { it.category == nameCategory }
    }
}