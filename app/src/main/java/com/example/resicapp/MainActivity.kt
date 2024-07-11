package com.example.resicapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import home.ContainerHomeActivity
import log.login.LoginActivity
import log.singUp.SingUpActivity
import user.UserSystem

class MainActivity : AppCompatActivity() {
    private lateinit var btnSingUp : Button
    private lateinit var btnLogin  : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        btnSingUp = findViewById(R.id.btn_sing_up)
        btnLogin = findViewById(R.id.btn_login)

        btnSingUp.setOnClickListener{
            val intent = Intent(this , SingUpActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener{
            val intent = Intent(this , LoginActivity::class.java)
            startActivity(intent)
        }
    }

}