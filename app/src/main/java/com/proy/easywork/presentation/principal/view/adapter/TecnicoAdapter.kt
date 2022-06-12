package com.proy.easywork.presentation.principal.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proy.easywork.R
import com.proy.easywork.data.model.response.VMBusqTecnico
import com.proy.easywork.data.model.response.VMBusqTecnicoGeneral
import com.squareup.picasso.Picasso

class TecnicoAdapter (val lista:List<VMBusqTecnicoGeneral>, val onClick: (VMBusqTecnicoGeneral)->Unit): RecyclerView.Adapter<TecnicoAdapter.TecnicoHolder>() {

    class TecnicoHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val imgTecnico: ImageView = v.findViewById(R.id.imgTecnico)
        val tvProfesion: TextView = v.findViewById(R.id.tvProfesion)
        val tvCantCliente: TextView = v.findViewById(R.id.tvCantClientes)
        val tvRating: TextView = v.findViewById(R.id.tvRating)
        val tvPerfil: TextView = v.findViewById(R.id.btPerfil)
        val tvDistancia: TextView = v.findViewById(R.id.tvDistancia)
        val tvTiempo: TextView = v.findViewById(R.id.tvTiempo)

        val rb : RatingBar = v.findViewById(R.id.rb)
        fun bind(tecnico:  VMBusqTecnicoGeneral, onClick: (VMBusqTecnicoGeneral) -> Unit) {
            var data = tecnico.datosTecnico
            tvNombre.text=data.nombreTecnico
            tvCantCliente.text=data.cantidadClientes
            tvRating.text=data.valoracion
            rb.rating= data.valoracion.toFloat()
            tvProfesion.text=data.categoria
            tvTiempo.text=tecnico.strTiempoViaje
            tvDistancia.text=tecnico.strDistancia
            Picasso.get().load(data.urlImagenTecnico).into(imgTecnico)
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