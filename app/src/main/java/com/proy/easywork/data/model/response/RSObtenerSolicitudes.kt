package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSObtenerSolicitudes (val Message: String, val data: VMDatosSolicitud) :
    Serializable

data class VMDatosSolicitud(
    val cantidadSolicitudesDia: Int,
    val cantidadSolicitudesDirectas: Int,
    val cantidadSolicitudesGenerales: Int): Serializable