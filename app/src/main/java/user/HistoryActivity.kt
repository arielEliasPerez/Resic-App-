package user

import adapter.HistoryAdapter
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.resicapp.R
import data.Product
import data.Purchase

class HistoryActivity : AppCompatActivity() {
    private lateinit var back : ImageButton
    private lateinit var search: SearchView
    private lateinit var tvMessage:TextView
    private lateinit var rv: RecyclerView
    private lateinit var reset : ImageButton

    private var userPurchases = UserSystem.userPurchases()
    private var userProducts  = UserSystem.userProducts()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history)

        tvMessage = findViewById(R.id.tv_h_message)
        reset  = findViewById(R.id.imb_reset)
        back   = findViewById(R.id.btn_back_history)
        search = findViewById(R.id.search_history)
        rv     = findViewById(R.id.rv_history)

        search()

        showHistory(userPurchases)

        back.setOnClickListener{
            finish()
        }

        reset.setOnClickListener{
            resetView()
            showHistory(userPurchases)
            reset.visibility = View.GONE
        }

    }

    private fun resetView() {
        reset.visibility = View.VISIBLE
        rv.visibility = View.VISIBLE
        tvMessage.visibility = View.GONE

    }

    private fun search() {
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

    fun showResult(search: String){
        val products = userProducts.filter {
            it.name.contains(search, ignoreCase = true)
        }

        if(products.isEmpty()) notFound(search) else productFound( search, products)
    }

    private fun productFound(search: String, products: List<Product>) {
        tvMessage.visibility = View.VISIBLE
        tvMessage.text = getString(R.string.found , search)

        val productId = products.map { it.id }

        showHistory(userPurchases.filter { it.productId in productId })
    }

    private fun notFound(search: String) {
        tvMessage.text = getString(R.string.products_not_found , search)
        tvMessage.visibility = View.VISIBLE
        rv.visibility = View.GONE
    }

    private fun showHistory(purchase : List<Purchase>) {
        rv.layoutManager = LinearLayoutManager(this)

        rv.adapter = HistoryAdapter(purchase.toList(), this)
    }

}