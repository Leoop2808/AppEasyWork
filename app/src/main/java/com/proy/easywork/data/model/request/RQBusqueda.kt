package com.proy.easywork.data.model.request

import java.io.Serializable

data class RQBusqueda (val codCategoria: String, val direccion: String, val latitud: Double, val longitud: Double, val codDistrito: String, val descripcionProblema: String, val codMedioPago: String, val codTipoBusqueda: String): Serializable