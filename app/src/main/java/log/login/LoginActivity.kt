package log.login

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.resicapp.R
import com.google.android.material.textfield.TextInputLayout
import home.ContainerHomeActivity
import repositories.UserRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var tilNickName : TextInputLayout
    private lateinit var tilPassword : TextInputLayout


    private lateinit var etNickName: EditText
    private lateinit var etPassword: EditText
    private lateinit var tvMessage: TextView
    private lateinit var btnSummit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tilNickName = findViewById(R.id.til_nick_name)
        tilPassword  = findViewById(R.id.til_password)

        etNickName = findViewById(R.id.et_nick_name)
        etPassword = findViewById(R.id.et_password)
        tvMessage  = findViewById(R.id.tv_log_error)
        btnSummit  = findViewById(R.id.btn_login)


        changeHelpText()

        etPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                checkCharacters()
                true
            } else {
                false
            }
        }

        btnSummit.setOnClickListener {
            checkCharacters()
        }
    }

    private fun changeHelpText() {
        etNickName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                tilNickName.helperText = null
            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                tilPassword.helperText = null
            }
        })
    }


    private fun checkCharacters(){
        val userName = etNickName.text.toString()
        val password = etPassword.text.toString()

        if (userName.isNotEmpty() || password.isNotEmpty()) login(userName , password)
        else temporaryMessage("Datos faltantes")
    }

    private fun login(userName: String, password: String) {

        val user = UserRepository.login(nickName = userName, password = password)

        user?.let {
            val intent = Intent(this, ContainerHomeActivity::class.java)
            user.logged = true
            startActivity(intent)

        } ?: run {

            temporaryMessage(message = "Usuario no encontrado :/")
        }

    }

    private fun temporaryMessage(message : String){
        tvMessage.text = message

        tvMessage.visibility = View.VISIBLE

        Handler(Looper.getMainLooper()).postDelayed({
            tvMessage.visibility = View.INVISIBLE
        }, 3000)
    }
}