package log.singUp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import character.ValidateText
import character.ValidateText.maxLength
import character.ValidateText.minLength
import character.ValidateText.validCharactersNick
import character.ValidateText.validCharactersPassword
import com.example.resicapp.R
import com.google.android.material.textfield.TextInputLayout
import home.ContainerHomeActivity
import repositories.UserRepository

class SingUpActivity : AppCompatActivity() {
    private lateinit var tilName : TextInputLayout
    private lateinit var tilSurName : TextInputLayout
    private lateinit var tilNickName : TextInputLayout
    private lateinit var tilPassword : TextInputLayout
    private lateinit var btnSingUp : Button

    private lateinit var etName : EditText
    private lateinit var etSurName : EditText
    private lateinit var etNickName: EditText
    private lateinit var etPassword: EditText

    private var name : String = ""
    private var surName : String  = ""
    private var nickname : String = ""
    private var password : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_sing_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tilName = findViewById(R.id.til_name)
        tilSurName = findViewById(R.id.til_surName)
        tilNickName = findViewById(R.id.til_nick_name)
        tilPassword  = findViewById(R.id.til_password)
        btnSingUp = findViewById(R.id.btn_sing_up)

        etNickName = findViewById(R.id.et_nick_name)
        etPassword = findViewById(R.id.et_password)
        etSurName  = findViewById(R.id.et_surName)
        etName  = findViewById(R.id.et_name)

        changeHelpText()

        btnSingUp.setOnClickListener{
            if(validateTextIsNotEmpty()) validateText()
        }

        etPassword.setOnEditorActionListener { _, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE || event?.action == KeyEvent.ACTION_DOWN && event.keyCode == KeyEvent.KEYCODE_ENTER) {
                if(validateTextIsNotEmpty()) validateText()
                true
            } else {
                false
            }
        }
    }

    private fun singUp() {
       if(UserRepository.addUser(nickname  , password , name , surName)){
           val intent = Intent(this, ContainerHomeActivity::class.java)
           startActivity(intent)
       }
    }

    private fun validateText() {
        var validLength = false

        var validCharacters = false

        val validNickName: Boolean = validNickname()

        if(validNickName) validLength = validateLength()

        if(validLength) validCharacters = validCharacters()

        if(validCharacters) singUp()
    }

    private fun validateTextIsNotEmpty() : Boolean{
        var aux = true
        name = etName.text.toString()
        surName  = etSurName.text.toString()
        nickname = etNickName.text.toString()
        password = etPassword.text.toString()

        if(surName.isEmpty()){
            aux = false
            tilSurNameError()
        }

        if(name.isEmpty()){
            aux = false
            tilNameError()
        }

        if (nickname.isEmpty()){
            aux = false
            tilNickNameError()
        }

        if (password.isEmpty()){
            aux = false
            tilPasswordError()
        }

        return aux

    }


    private fun validNickname(): Boolean {
       if(ValidateText.nickNameExists(nickname)){
           tilNickNameError("nick name existente prueba con otro")
           return false
       }
        return true
    }

    private fun validCharacters(): Boolean {
        var aux = true
        if(!validCharactersNick(nickname)) {
            tilNickNameError("caracteres validos: letras , numeros y _ ")
            aux = false
        }

        if(!validCharactersPassword(password)){
            tilPasswordError("tu contraseña solo puede contener letras , numeros y @_")
            aux = false
        }

        return aux
    }

    private fun validateLength(): Boolean {
        if(!minLength()) return false

        if(!maxLength()) return false

        return true
    }

    private fun maxLength(): Boolean {
        var aux = true

        if (maxLength(name)) {
            tilNameError("El nombre es muy largo")
            aux = false
        }

        if (maxLength(nickname)) {
            tilNameError("tu nickName es muy largo")
            aux = false
        }

        if (maxLength(password)) {
            tilNameError("tu contraseña es demasiado larga ")
            aux = false
        }

        return aux
    }

    private fun minLength(): Boolean {
        var aux = true

        if (minLength(name)) {
            tilNameError("nombre demasiado corto")
            aux = false

        }

        if (minLength(nickname)) {
            tilNickNameError("nickName demasiado corto")
            aux = false

        }

        if (minLength(password)) {
            tilPasswordError("contraseña demasiada corto")
            aux = false
        }
        return aux
    }


    private fun changeHelpText(){
        etSurName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) tilSurName.helperText = "Requerido" else tilSurName.helperText = null
            }
        })

        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) tilName.helperText = "Requerido" else tilName.helperText = null
            }
        })

        etNickName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) tilNickName.helperText = "Requerido" else{
                    minLength(s.toString())
                    tilNickName.helperText = null
                }

            }
        })

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (s.isNullOrEmpty()) tilPassword.helperText = "Requerido" else tilPassword.helperText = null
            }
        })
    }

    private fun tilNameError(message : String = "no puede estar vacio"){
        tilName.error = message
        Handler(Looper.getMainLooper()).postDelayed({
            tilName.error = null
        }, 3000)
    }

    private fun tilSurNameError(message : String = "no puede estar vacio"){
        tilSurName.error = message
        Handler(Looper.getMainLooper()).postDelayed({
            tilSurName.error = null
        }, 3000)
    }

    private fun tilNickNameError(message : String = "no puede estar vacio"){
        tilNickName.error = message
        Handler(Looper.getMainLooper()).postDelayed({
            tilNickName.error = null
        }, 3000)
    }

    private fun tilPasswordError(message : String = "no puede estar vacio"){
        tilPassword.error = message
        Handler(Looper.getMainLooper()).postDelayed({
            tilPassword.error = null
        }, 3000)
    }

}

