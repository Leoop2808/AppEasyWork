package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSBusquedaTecnico (val Message: String, val data: MutableList<VMBusqTecnico> = mutableListOf()) : Serializable


data class VMBusqTecnico(val codUsuarioTecnico: String,
                         val nombreTecnico:String,
                         val urlImgTecnico: String,
                         val codUsuarioTecnicoCategoria: String,
                         val codCategoria: String,
                         val categoria: String,
                         val cantidadClientes: String,
                         val valoracion: String): Serializable