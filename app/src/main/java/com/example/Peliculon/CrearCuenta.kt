package com.example.Peliculon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.Peliculon.R

class CrearCuenta : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cuenta)

        var Login2 = findViewById<Button>(R.id.Login2)

        Login2.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
        }

    }
}