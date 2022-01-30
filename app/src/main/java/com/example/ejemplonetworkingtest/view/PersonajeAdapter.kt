package com.example.ejemplonetworkingtest.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ejemplonetworkingtest.R
import com.example.ejemplonetworkingtest.model.PersonajeModel

class PersonajeAdapter(private var personajes: List<PersonajeModel>) :
    RecyclerView.Adapter<PersonajeAdapter.MViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_personaje, parent, false)
        return MViewHolder(view)
    }

    override fun onBindViewHolder(vh: MViewHolder, position: Int) {
        vh.bind(personajes[position])
    }

    override fun getItemCount(): Int {
        return personajes.size
    }

    fun update(data: List<PersonajeModel>) {
        personajes = data
        notifyDataSetChanged()
    }

    class MViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val textViewName: TextView = view.findViewById(R.id.textViewName)
        private val imageView: ImageView = view.findViewById(R.id.imageView)
        fun bind(personaje: PersonajeModel) {
            textViewName.text = personaje.getNombreCorto()
            Glide.with(imageView.context).load(personaje.imagen).into(imageView)
        }
    }
}