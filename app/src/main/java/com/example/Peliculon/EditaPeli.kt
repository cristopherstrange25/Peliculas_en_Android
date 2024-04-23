package com.example.Peliculon

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Firebase
import com.google.firebase.database.database

class EditaPeli : AppCompatActivity() {
    val database = Firebase.database
    val myRef = database.getReference("Peliculas")
  //  val database = Firebase.database
    //val myRef = database.getReference("Peliculas")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edita_peli)

        val pelicula = findViewById<EditText>(R.id.nombreE)
        val genero = findViewById<EditText>(R.id.generoE)
        val anio = findViewById<EditText>(R.id.anioE)
     //   val editar123 = findViewById<Button>(R.id.Editare)
        val eliminar123 = findViewById<Button>(R.id.eliminare)
        val imagene = findViewById<ImageView>(R.id.imagenE)

        val parametros = intent.extras
        pelicula.setText(parametros?.getCharSequence("pelicula").toString())
        genero.setText(parametros?.getCharSequence("genero").toString())
        anio.setText(parametros?.getCharSequence("anio").toString())


        if (parametros?.getCharSequence("genero").toString()== "Terror"){
            imagene.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.terror))
        }else if(parametros?.getCharSequence("genero").toString()== "Infantil"){
            imagene.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.infa))
        }else if(parametros?.getCharSequence("genero").toString() == "Accion"){
            imagene.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.acc))
        }


        val editar123 = findViewById<Button>(R.id.Editare)
        editar123.setOnClickListener {
            val peliculaEditada = PeliCampos(pelicula.text.toString(), genero.text.toString(), anio.text.toString())
            val peliculaId = parametros?.getCharSequence("id").toString()

            // Crear un mapa para actualizar múltiples campos a la vez
            val actualizacionPelicula = HashMap<String, Any>()
            actualizacionPelicula["pelicula"] = peliculaEditada.pelicula.toString()
            actualizacionPelicula["genero"] = peliculaEditada.genero.toString()
            actualizacionPelicula["anio"] = peliculaEditada.anio.toString()

            // Actualizar los valores en Firebase
            myRef.child(peliculaId).updateChildren(actualizacionPelicula).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Película editada correctamente", Toast.LENGTH_LONG).show()
                    // Actualizar la imagen según el género de la película editada
                    when (genero.text.toString()) {
                        "Terror" -> imagene.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.terror))
                        "Infantil" -> imagene.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.infa))
                        "Accion" -> imagene.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.acc))
                        else -> imagene.setImageDrawable(null)
                    }
                } else {
                    Toast.makeText(this, "Error al editar película: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
            startActivity(Intent(this, MenuPrincipal::class.java))
            finish()
        }

        eliminar123.setOnClickListener{
            val builder: AlertDialog.Builder = MaterialAlertDialogBuilder(this )
            builder.setMessage("¿Estas seguro de eliminar la pelicula?")
                .setPositiveButton("Aceptar", DialogInterface.OnClickListener{
                    dialog, id ->
                    myRef.child(parametros?.getCharSequence("id").toString()).removeValue().addOnCompleteListener {
                            task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Pelicula eliminada correctamente", Toast.LENGTH_LONG).show()
                            finish()
                        }else{
                            Toast.makeText(this, "Error al eliminar pelicula" + task.exception?.message, Toast.LENGTH_LONG).show()
                        }
                    }

                    }).setNegativeButton("Cancelar", DialogInterface.OnClickListener{dialog, id ->})
            builder.show()

        }


    }
}