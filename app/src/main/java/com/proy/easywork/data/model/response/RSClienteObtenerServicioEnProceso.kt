package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSClienteObtenerServicioEnProceso (val Message: String, val data: VMClienteServicioEnProceso): Serializable
data class VMClienteServicioEnProceso(
    val idServicio: Int,
    val codUsuarioTecnico: String,
    val nombreUsuarioTecnico: String,
    val urlImagenUsuarioTecnico:String,
    val codCategoriaServicio: String,
    val nombreCategoriaServicio: String,
    val descripcionProblema: String,
    val nombreDistrito: String,
    val codMedioPago: String,
    val nombreMedioPago: String,
    val direccion: String): Serializable