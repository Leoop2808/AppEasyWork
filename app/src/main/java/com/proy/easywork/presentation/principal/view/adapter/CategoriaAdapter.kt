package com.proy.easywork.presentation.principal.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.proy.easywork.R
import com.proy.easywork.data.db.entity.CategoriaServicio

class CategoriaAdapter(val lista:List<CategoriaServicio>, val onClick: (CategoriaServicio)->Unit): RecyclerView.Adapter<CategoriaAdapter.CategoriaHolder>() {

    class CategoriaHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvNombre: TextView = v.findViewById(R.id.tvCateg)
        val imgCat: ImageView = v.findViewById(R.id.img)
        val clCategoria: LinearLayout = v.findViewById(R.id.lnCategoria)
        fun bind(categoria:  CategoriaServicio, onClick: (CategoriaServicio) -> Unit) {
            tvNombre.text=categoria.nombreImgCategoriaServicio

            clCategoria.setOnClickListener { onClick(categoria) }

            when(categoria.codCategoriaServicio){
                "1"-> imgCat.setImageResource(R.drawable.electricidad)
                "2"-> imgCat.setImageResource(R.drawable.carpinteria)
                "3"-> imgCat.setImageResource(R.drawable.pintura)
                "5"-> imgCat.setImageResource(R.drawable.alba_ileria)
                "6"-> imgCat.setImageResource(R.drawable.gasfiteria)
                "7"-> imgCat.setImageResource(R.drawable.limpieza)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriaHolder {
        val inflatedView = LayoutInflater.from(parent.context).inflate(R.layout.item_categoria, parent, false)
        return CategoriaHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: CategoriaHolder, position: Int) {
        holder.bind(lista[position], onClick)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}