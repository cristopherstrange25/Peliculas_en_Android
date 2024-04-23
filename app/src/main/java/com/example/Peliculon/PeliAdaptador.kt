package com.example.Peliculon
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat

class PeliAdaptador( private val context: Activity, private val arrayList: ArrayList<Peliculas> ):
    ArrayAdapter<Peliculas>(context, R.layout.item, arrayList)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.item, null)
        view.findViewById<TextView>(R.id.nombrePeli).text = arrayList[position].pelicula
        view.findViewById<TextView>(R.id.generoPeli).text = arrayList[position].genero
        view.findViewById<TextView>(R.id.anioPeli).text = arrayList[position].anio

        //return super.getView(position, convertView, parent)
        if (arrayList[position].genero == "Terror"){
            view.findViewById<ImageView>(R.id.imagePeli).setImageDrawable(ContextCompat.getDrawable(context,
                R.drawable.terror
            ))
        }else if(arrayList[position].genero == "Infantil"){
            view.findViewById<ImageView>(R.id.imagePeli).setImageDrawable(ContextCompat.getDrawable(context,
                R.drawable.infa
            ))
        }else if(arrayList[position].genero == "Accion"){
            view.findViewById<ImageView>(R.id.imagePeli).setImageDrawable(ContextCompat.getDrawable(context,
                R.drawable.acc
            ))
        }
        return view
    }
}