package user

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.resicapp.R
import data.Product
import data.Purchase
import repositories.ProductRepository
import repositories.PurchaseRepository

class PurchaseDetailsActivity : AppCompatActivity() {
    private lateinit var productLogo   : ImageView
    private lateinit var productTitle  : TextView
    private lateinit var productAuthor : TextView
    private lateinit var productClassification : TextView
    private lateinit var productPrecious   : TextView
    private lateinit var productCommission : TextView
    private lateinit var productAmount  : TextView
    private lateinit var dateOfPurchase : TextView
    private lateinit var productReleasedDate : TextView
    private lateinit var productCategory : TextView
    private lateinit var imbBack : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_purchase_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val id = intent.getLongExtra("idPurchase" , 0)
        val purchase = PurchaseRepository.getId(id)

        val product = ProductRepository.getById(purchase.productId)

        receipt(product, purchase)

        imbBack.setOnClickListener{
            finish()
        }
    }

    private fun receipt(product: Product, purchase: Purchase) {
        productLogo = findViewById(R.id.itemLogo)
        productTitle = findViewById(R.id.itemTitle)
        productAuthor = findViewById(R.id.itemAuthor)
        productReleasedDate = findViewById(R.id.itemReleasedDate)
        productCategory   = findViewById(R.id.itemClategory)
        productClassification = findViewById(R.id.itemClasification)
        productPrecious   = findViewById(R.id.itemPrice)
        productCommission = findViewById(R.id.tv_comision)
        productAmount = findViewById(R.id.tv_final_price)
        dateOfPurchase    = findViewById(R.id.tv_date_of_purchase)
        imbBack = findViewById(R.id.imb_purchase_details)

        productLogo.setImageResource(product.logo)
        productTitle.text = getString(R.string.product_title , product.name)
        productAuthor.text = getString(R.string.product_author, product.author)
        productReleasedDate.text = getString(R.string.product_released_date , product.releasedDate)
        productCategory.text = getString(R.string.product_category , product.category)
        productClassification.text = getString(R.string.product_classification , product.clasification.toString().lowercase())
        productPrecious.text = getString(R.string.product_price , product.price.toString())
        productCommission.text = getString(R.string.product_commission ,  purchase.amount.minus(product.price).toString())
        productAmount.text = getString(R.string.product_amount , purchase.amount.toString())
        dateOfPurchase.text = getString(R.string.product_date_of_purchase , purchase.createdDate)
    }
}