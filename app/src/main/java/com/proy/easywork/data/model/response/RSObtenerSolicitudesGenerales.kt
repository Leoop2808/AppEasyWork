package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSObtenerSolicitudesGenerales (val Message: String, val data: MutableList<VMSolicitudGeneral> = mutableListOf()) : Serializable


data class VMSolicitudGeneral(
                            val idServicio: Int,
                            val codUsuarioCliente:String,
                            val nombreUsuarioCliente: String,
                            val codCategoriaServicio: String,
                            val nombreCategoriaServicio: String,
                            val descripcionProblema: String,
                            val codDistrito: String,
                            val nombreDistrito: String,
                            val nombreMedioPago: String,
                            val direccion: String): Serializable