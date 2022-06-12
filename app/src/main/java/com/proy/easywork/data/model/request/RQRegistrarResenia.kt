package com.proy.easywork.data.model.request

import java.io.Serializable

data class RQRegistrarResenia (val idTrabajadorCategoriaServicio: Int,
                               val comentario: String,
                               val valoracion: Int) : Serializable