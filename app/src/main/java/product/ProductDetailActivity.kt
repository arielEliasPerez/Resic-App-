package product

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.resicapp.R
import com.google.android.material.snackbar.Snackbar
import data.Product
import data.ProductClasification
import prices.BronzePrice
import prices.GoldPrice
import prices.PlatinumPrice
import price.PriceCalculator
import prices.SilverPrice
import repositories.ProductRepository
import repositories.PurchaseRepository
import repositories.UserRepository

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var buy    : Button
    private lateinit var name   : TextView
    private lateinit var price  : TextView
    private lateinit var image  : ImageView
    private lateinit var author : TextView
    private lateinit var synopsis: TextView
    private lateinit var category: TextView
    private lateinit var ratingBar: RatingBar
    private lateinit var clasification: TextView
    private lateinit var releasedDate: TextView
    private lateinit var imbBack : ImageButton

    private val user = UserRepository.getUserLogged()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val idProduct =  intent.getLongExtra("idProduct" , 0)

        val product = ProductRepository.getById(idProduct)

        buy    = findViewById(R.id.btn_buy)
        image  = findViewById(R.id.item_p_logo)
        name   = findViewById(R.id.item_d_title)
        author = findViewById(R.id.item_d_author)
        price  = findViewById(R.id.item_d_price)
        imbBack = findViewById(R.id.imb_back_product_detail)

        releasedDate   = findViewById(R.id.item_d_releasedDate)
        category  = findViewById(R.id.item_d_clategory)
        synopsis  = findViewById(R.id.item_d_synopsis)
        ratingBar = findViewById(R.id.rating_d_bar)
        clasification = findViewById(R.id.item_d_clasification)

        synopsis.movementMethod = ScrollingMovementMethod()

        renameItem(product)

        imbBack.setOnClickListener{
            finish()
        }

        clasification.setOnClickListener{
            // Crear el diálogo
            classificationInformation()
        }

        buy.setOnClickListener {
            if(!duplicateProduct(product)) {
                buy(product)
            }else {
                Snackbar.make(findViewById(android.R.id.content), "Ya tienes este articulo en tu libreria", Snackbar.LENGTH_SHORT).show()
            }
        }


    }

    private fun buy(product: Product){
        val totalPrice = calculateTotalPrice(product)

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea continuar con la compra?")
        builder.setMessage("Precio del producto : $${product.price} \n" +
                "Precio final: $$totalPrice")

        builder.setPositiveButton("Comprar") { dialog, _ ->
            successfulPurchase(totalPrice , product.id)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        // Mostrar el diálogo
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun classificationInformation() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Clasificacion:")
        builder.setMessage(
            "Gold: comisión del 2% \n" +

                    "Silver: entre 15:00 hs y las 22:30 hs " +
                    " comisión del 1%, fuera de este horario comisión del 3% \n" +

                    "Platinum: fin de semana comisión 3%, dia de semana comisión del 0.75% \n" +

                    "Bronze: no aplica comisión"
        )

        builder.setPositiveButton("Cerrar") { dialog, _ ->
            // Acción para el botón "Aceptar"
            dialog.dismiss()
        }

        // Mostrar el diálogo
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun calculateTotalPrice(product: Product): Double {
        val productPrice: PriceCalculator = when (product.clasification) {
            ProductClasification.GOLD -> GoldPrice(product.id, product.price)
            ProductClasification.SILVER -> SilverPrice(product.id, product.price)
            ProductClasification.PLATINUM -> PlatinumPrice(product.id, product.price)
            else -> BronzePrice(product.id, product.price)
        }

        return productPrice.calculateTotalPrice()
    }

    private fun successfulPurchase(totalPrice: Double, idProduct: Long) {
        val successfulPurchase = PurchaseRepository.processPurchase(totalPrice, this.user!! , idProduct)

        if (successfulPurchase) {
            Snackbar.make(findViewById(android.R.id.content), "Compra exitosa!!", Snackbar.LENGTH_SHORT).show()

        } else {

            Snackbar.make(findViewById(android.R.id.content), "Compra fallida :(", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun duplicateProduct(product: Product): Boolean {
        val purchase = PurchaseRepository.get().filter { it.userId == user!!.id }

        return purchase.any{it.productId == product.id}
    }

    private fun renameItem(product: Product) {
        image.setImageResource(product.logo)
        author.text = getString(R.string.product_author, product.author)
        name.text   = product.name
        price.text  = getString(R.string.product_price, product.price.toString())
        releasedDate.text  = getString(R.string.product_released_date, product.releasedDate)
        category.text = product.category
        synopsis.text = product.synopsis
        ratingBar.rating   = product.stars
        clasification.text = getString(R.string.product_classification, product.clasification.name.lowercase())


        category.setOnClickListener{
            val intent = Intent(this, FindProductsActivity::class.java).putExtra("NameCategory", product.category)
            startActivity(intent)
        }
    }

}