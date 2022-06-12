package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSTecnicoObtenerServicioEnProceso (val Message: String, val data: VMTecnicoServicioEnProceso):
    Serializable
data class VMTecnicoServicioEnProceso(
    val idServicio: Int,
    val codUsuarioCliente: String,
    val nombreUsuarioCliente: String,
    val codCategoriaServicio:String,
    val nombreCategoriaServicio: String,
    val descripcionProblema: String,
    val nombreDistrito: String,
    val codMedioPago: String,
    val nombreMedioPago: String,
    val direccion: String): Serializable