package com.example.Peliculon

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.Peliculon.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth


class Login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = Firebase.auth
        var inicio = findViewById<Button>(R.id.BtnInicio)
        var crear = findViewById<Button>(R.id.BtnCrear)
        var reset = findViewById<Button>(R.id.BtnReset)
        var email = findViewById<EditText>(R.id.email)
        var pass = findViewById<EditText>(R.id.pass)

        inicio. setOnClickListener{
            if(email.text.toString() != "" && pass.text.toString() != ""){
                auth.signInWithEmailAndPassword(email.text.toString(), pass.text.toString()).addOnCompleteListener {
                    task ->
                    if(task.isSuccessful) {
                        startActivity(Intent(this, MenuPrincipal::class.java).putExtra("saludo", "Menu Principal"))
                        finish()
                    }else{
                        Toast.makeText(this, "Error: "+ task.exception!!.message.toString(), Toast.LENGTH_LONG).show()
                    }
                }

            }else{
                Toast.makeText(this, "Ingresa correo y contraseña", Toast.LENGTH_LONG).show()

            }
        }
        crear.setOnClickListener{
            startActivity(Intent(this, CrearCuenta::class.java))
        }
        reset.setOnClickListener{
            startActivity(Intent(this, RecuperarPass::class.java))
        }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser == null)
        {
            Toast.makeText(this, "No hay usuarios autentificados", Toast.LENGTH_LONG).show()

        }else{
            startActivity(Intent(this, MenuPrincipal::class.java))
            finish()
        }
    }
}