package com.proy.easywork.presentation.principal.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proy.easywork.R
import com.proy.easywork.data.model.response.VMPerfilComentario
import com.proy.easywork.data.model.response.VMcomentario

class ComentarioAdapter(val lista:List<VMPerfilComentario>, val onClick: (VMPerfilComentario)->Unit) : RecyclerView.Adapter<ComentarioAdapter.ComentarioHolder>(){

    class ComentarioHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNombre: TextView = v.findViewById(R.id.tvNombre)
        val tvComentario: TextView = v.findViewById(R.id.tvComentario)
        val tvServicio: TextView = v.findViewById(R.id.tvServicio)
        val rb : RatingBar = v.findViewById(R.id.rb)
        fun bind(tecnico:  VMPerfilComentario, onClick: (VMPerfilComentario) -> Unit) {
            tvNombre.text="Por ${tecnico.nombreUsuario} el ${tecnico.fechaComentario}"
            tvComentario.text=tecnico.comentario
            rb.rating= tecnico.cantEstrellas *1.0F

            if(tecnico.flgServicioVerificado){
                tvServicio.visibility=View.VISIBLE
            }else{
                tvServicio.visibility=View.GONE
            }
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