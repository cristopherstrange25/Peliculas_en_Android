package com.example.Peliculon

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ListView
import android.widget.Toast
import androidx.core.view.MenuProvider
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class MenuPrincipal : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth;
    val database = Firebase.database
    val myRef = database.getReference("Peliculas")
    lateinit var peliculas: ArrayList<Peliculas>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        auth = Firebase.auth

        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar6)
        setSupportActionBar(toolbar)

        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId)
                {
                    R.id.perfil -> true
                    R.id.salir -> {
                        auth.signOut()
                        startActivity(Intent(this@MenuPrincipal, Login::class.java))
                        finish()

                        true
                    }
                    else ->
                        false
                }
            }
        })
// Read from the database
        myRef.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                peliculas = ArrayList<Peliculas>()
                val value = snapshot.value.toString()
                Log.d(TAG, "Value is: " + value)
                snapshot.children.forEach{
                    item ->
                        val pelicula = Peliculas(item.child("pelicula").value.toString(),
                                                item.child("genero").value.toString(),
                                                item.child("anio").value.toString(),
                                                item.key.toString())
                        peliculas.add(pelicula)

                }
                llenaPeliculas()


            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
            val lista = findViewById<ListView>(R.id.Lista)
            lista.setOnItemClickListener { parent, view, position, id ->
                Toast.makeText(this@MenuPrincipal, peliculas[position].pelicula.toString(), Toast.LENGTH_LONG).show()
                Log.d("click in item", peliculas[position].pelicula.toString())

                startActivity(Intent(this, EditaPeli::class.java)
                    .putExtra("pelicula", peliculas[position].pelicula.toString())
                    .putExtra("genero", peliculas[position].genero.toString())
                    .putExtra("anio", peliculas[position].anio.toString())
                    .putExtra("id", peliculas[position].ides)

                )


            }

    val btnAgregarPelis = findViewById<com.google.android.material.floatingactionbutton.FloatingActionButton>(R.id.AgregarPelis)
    btnAgregarPelis.setOnClickListener{
        val peliVacia = PeliCampos("Edita Pelicula", "Edita Genero", "Edita Año")
        myRef.push().setValue(peliVacia)

    }
    }
    fun llenaPeliculas(){
        val adaptador = PeliAdaptador(this, peliculas)
        val lista = findViewById<ListView>(R.id.Lista)
        lista.adapter = adaptador
    }
}