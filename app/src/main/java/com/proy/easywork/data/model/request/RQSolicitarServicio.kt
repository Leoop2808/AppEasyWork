package com.proy.easywork.data.model.request

import java.io.Serializable

data class RQSolicitarServicio  (val idUsuarioTecnicoCategoria: Int,
                            val codDistrito: String,
                            val codMedioPago: String,
                            val codCategoriaServicio: String,
                            val codTipoBusqueda: String,
                            val direccion: String,
                            val descripcionProblema: String,
                            val latitud: Double,
                            val longitud: Double): Serializable