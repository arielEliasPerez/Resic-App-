package user

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.resicapp.R
import data.Product
import repositories.ProductRepository
import repositories.PurchaseRepository

class ProductsUserDetailsActivity : AppCompatActivity() {
    private lateinit var imLogo  : ImageView
    private lateinit var tvTitle : TextView
    private lateinit var tvAuthor : TextView
    private lateinit var tvCategory : TextView
    private lateinit var tvReleasedDate : TextView
    private lateinit var tvSynopsis : TextView
    private lateinit var btnDetailPurchase : Button
    private lateinit var imbBack : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_products_user_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imLogo = findViewById(R.id.item_p_logo)
        tvTitle = findViewById(R.id.itemTitle)
        tvAuthor  = findViewById(R.id.itemAuthor)
        tvCategory = findViewById(R.id.itemClategory)
        tvReleasedDate = findViewById(R.id.itemReleasedDate)
        tvSynopsis     =  findViewById(R.id.itemSynopsis)
        btnDetailPurchase = findViewById(R.id.btn_detalle)
        imbBack = findViewById(R.id.imb_back_product_user)

        val id = intent.getLongExtra("idPurchase" , 0)

        val purchase = PurchaseRepository.getId(id)
        val product = ProductRepository.getById(purchase.productId)

        renameItems(product)

        imbBack.setOnClickListener{
            finish()
        }

        btnDetailPurchase.setOnClickListener {
            val intent = Intent(this , PurchaseDetailsActivity:: class.java )
            intent.putExtra("idPurchase" , purchase.id)
            startActivity(intent)
        }

    }

    private fun renameItems(product: Product) {
        imLogo.setImageResource(product.logo)
        tvTitle.text = product.name
        tvAuthor.text = getString(R.string.product_author, product.author)
        tvCategory.text = getString(R.string.product_category, product.category)
        tvReleasedDate.text = getString(R.string.product_released_date, product.releasedDate)
        tvSynopsis.text = product.synopsis
    }
}