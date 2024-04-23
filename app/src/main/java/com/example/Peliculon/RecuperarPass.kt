package com.example.Peliculon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.Peliculon.R

class RecuperarPass : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recuperar_pass)

        var Login = findViewById<Button>(R.id.Login)

       Login.setOnClickListener{
            startActivity(Intent(this, Login::class.java))
       }
    }
}