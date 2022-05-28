package com.proy.easywork.presentation.principal.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proy.easywork.R

class ComentarioAdapter(val lista:List<String>, val onClick: (String)->Unit) : RecyclerView.Adapter<ComentarioAdapter.ComentarioHolder>(){

    class ComentarioHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val tvComentario: TextView = v.findViewById(R.id.tvComentario)

        val rb : RatingBar = v.findViewById(R.id.rb)
        fun bind(tecnico:  String, onClick: (String) -> Unit) {
            tvNombre.text=tecnico
            tvComentario.text="Excelente servicio, lo recomiento"
            rb.rating= 3.5F
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioHolder {
        val inflatedView= LayoutInflater.from(parent.context).inflate(R.layout.item_comentario, parent, false)
        return ComentarioHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ComentarioHolder, position: Int) {
            holder.bind(lista[position], onClick)
    }

    override fun getItemCount(): Int {

        return lista.size
    }
}