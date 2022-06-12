package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSBuscarTecnicosGeneral (val Message: String, val data: MutableList<VMBusqTecnicoGeneral> = mutableListOf()) :
    Serializable


data class VMBusqTecnicoGeneral(val datosTecnico: VMDatosTecnico,
                         val strDistancia:String,
                         val strTiempoViaje: String): Serializable

data class VMDatosTecnico (val codUsuarioTecnico: String,
                           val nombreTecnico: String,
                           val urlImagenTecnico: String,
                           val idTecnicoCategoriaServicio: Int,
                           val codCategoria: String,
                           val categoria: String,
                           val cantidadClientes: String,
                           val valoracion: String): Serializable