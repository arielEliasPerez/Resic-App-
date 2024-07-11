package user

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.resicapp.MainActivity
import com.example.resicapp.R
import data.ProductType
import data.Purchase
import data.User
import repositories.ProductRepository
import repositories.PurchaseRepository
import repositories.UserRepository

class UserActivity : AppCompatActivity() {
    private lateinit var back : ImageButton
    private lateinit var labelUserName : TextView
    private lateinit var etUserName : EditText
    private lateinit var tvUserMoney: TextView
    private lateinit var tvUserSpent:TextView
    private lateinit var tvQuantityBook:TextView
    private lateinit var tvQuantityDisc:TextView
    private lateinit var tvSpentBook:TextView
    private lateinit var tvSpentDisc:TextView
    private lateinit var tvLastPurchase:TextView
    private lateinit var tvShowHistory:TextView
    private lateinit var tvLogOff:TextView
    private lateinit var tvUserCreateDate:TextView
    private lateinit var tvQuantityProduct : TextView

    private var user = UserSystem.user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_user)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        init()

        renameItem(user!!)

        back.setOnClickListener{
            finish()
        }

        tvShowHistory.setOnClickListener{
            val intent = Intent(this,HistoryActivity::class.java)
            startActivity(intent)
        }

        tvLogOff.setOnClickListener{
            confirm()
        }


    }

    private fun confirm() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Cerrar Sesion")

        builder.setMessage("Deseas cerrar sesion?")

        builder.setPositiveButton("Aceptar") { dialog, _ ->
            user!!.logged = false
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        }

        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }

        // Mostrar el diálogo
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun renameItem(user: User) {
        labelUserName.text = user.name
        etUserName.setText(user.nickName)
        tvUserMoney.text = getString(R.string.cash , user.money.toString())
        tvUserSpent.text = getString(R.string.cash , UserSystem.getSpent().toString())
        tvQuantityBook.text = getString(R.string.count , UserSystem.getQuantityBook().toString())
        tvQuantityDisc.text =  getString(R.string.count , UserSystem.getQuantityDisc().toString())
        tvSpentBook.text = getString(R.string.cash , UserSystem.getSpentBook().toString())
        tvSpentDisc.text = getString(R.string.cash , UserSystem.getSpentDisc().toString())
        tvUserCreateDate.text  = getString(R.string.user_create_data , user.createdDate)
        tvQuantityProduct.text = getString(R.string.count , UserSystem.getQuantityProducts().toString())
        if(UserSystem.getLastPurchase().toString().isEmpty()) lastPurchase() else lastPurchase("no hay compras")
    }

    private fun lastPurchase(text : String = UserSystem.getLastPurchase().toString() ) {
        tvLastPurchase.text =
            getString(R.string.last_purchase, text)
    }

    private fun init(){
        back = findViewById(R.id.back_user)
        labelUserName = findViewById(R.id.tv_user)
        etUserName = findViewById(R.id.et_c_userName)
        tvUserMoney = findViewById(R.id.tv_user_money)
        tvUserSpent = findViewById(R.id.tv_user_spent_money)
        tvQuantityBook = findViewById(R.id.tv_quantity_user_books)
        tvQuantityDisc = findViewById(R.id.tv_quantity_user_disc)
        tvSpentBook = findViewById(R.id.tv_spent_on_book)
        tvSpentDisc = findViewById(R.id.tv_spent_on_disc)
        tvLastPurchase = findViewById(R.id.tv_last_purchase)
        tvShowHistory = findViewById(R.id.tv_history)
        tvLogOff = findViewById(R.id.tv_log_off)
        tvUserCreateDate = findViewById(R.id.tv_user_created_date)
        tvQuantityProduct = findViewById(R.id.tv_quantity_user_products)
    }
}