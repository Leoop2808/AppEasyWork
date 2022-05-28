package com.proy.easywork.presentation.principal.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proy.easywork.R

class TecnicoAdapter (val lista:List<String>, val onClick: (String)->Unit): RecyclerView.Adapter<TecnicoAdapter.TecnicoHolder>() {

    class TecnicoHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val imgTecnico: ImageView = v.findViewById(R.id.imgTecnico)
        val tvProfesion: TextView = v.findViewById(R.id.tvProfesion)
        val tvCantCliente: TextView = v.findViewById(R.id.tvCantClientes)
        val tvRating: TextView = v.findViewById(R.id.tvRating)
        val tvPerfil: TextView = v.findViewById(R.id.btPerfil)

        val rb : RatingBar = v.findViewById(R.id.rb)
        fun bind(tecnico:  String, onClick: (String) -> Unit) {
            tvNombre.text=tecnico
            tvCantCliente.text="4"
            tvRating.text="3"
            rb.rating= 3.5F
            tvProfesion.text="Gasfitero"
            tvPerfil.setOnClickListener { onClick(tecnico) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TecnicoHolder {
        val inflatedView= LayoutInflater.from(parent.context).inflate(R.layout.item_tecnico, parent, false)
        return TecnicoHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: TecnicoHolder, position: Int) {
        holder.bind(lista[position], onClick)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}