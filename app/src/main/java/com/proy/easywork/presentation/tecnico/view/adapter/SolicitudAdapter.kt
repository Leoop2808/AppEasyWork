package com.proy.easywork.presentation.tecnico.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.proy.easywork.R
import com.proy.easywork.data.model.response.VMSolicitudGeneral
import com.squareup.picasso.Picasso

class SolicitudAdapter (val lista:List<VMSolicitudGeneral>, val onClick: (VMSolicitudGeneral)->Unit): RecyclerView.Adapter<SolicitudAdapter.SolicitudHolder>() {

    class SolicitudHolder(v: View) : RecyclerView.ViewHolder(v) {
        val imgCategoria: ImageView = v.findViewById(R.id.imgCategoria)
        val tvNombreCliente: TextView = v.findViewById(R.id.tvNombreCliente)
        val tvDescripcionProblema: TextView = v.findViewById(R.id.tvDescripcionProblema)
        val tvDistrito: TextView = v.findViewById(R.id.tvDistrito)
        val tvMedioPago: TextView = v.findViewById(R.id.tvMedioPago)
        val tvDireccion: TextView = v.findViewById(R.id.tvDireccion)
        val btAceptarSolicitud: TextView = v.findViewById(R.id.btAceptarSolicitud)

        fun bind(solicitud: VMSolicitudGeneral, onClick: (VMSolicitudGeneral) -> Unit) {
            when(solicitud.codCategoriaServicio){
                "1"-> Picasso.get().load(R.drawable.electricidad).into(imgCategoria)
                "2"-> Picasso.get().load(R.drawable.carpinteria).into(imgCategoria)
                "3"-> Picasso.get().load(R.drawable.pintura).into(imgCategoria)
                "5"-> Picasso.get().load(R.drawable.alba_ileria).into(imgCategoria)
                "6"-> Picasso.get().load(R.drawable.gasfiteria).into(imgCategoria)
                "7"-> Picasso.get().load(R.drawable.limpieza).into(imgCategoria)
            }

            tvNombreCliente.text=solicitud.nombreUsuarioCliente
            tvDescripcionProblema.text=solicitud.descripcionProblema
            tvDistrito.text="En " + solicitud.nombreDistrito
            tvMedioPago.text="Paga con " + solicitud.nombreMedioPago
            tvDireccion.text="En " + solicitud.direccion
            btAceptarSolicitud.setOnClickListener { onClick(solicitud) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolicitudHolder {
        val inflatedView= LayoutInflater.from(parent.context).inflate(R.layout.item_solicitud, parent, false)
        return SolicitudHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: SolicitudHolder, position: Int) {
        holder.bind(lista[position], onClick)
    }

    override fun getItemCount(): Int {
        return lista.size
    }
}