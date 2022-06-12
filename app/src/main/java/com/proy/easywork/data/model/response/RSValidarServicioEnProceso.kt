package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSValidarServicioEnProceso (val Message: String, val data: VMValidarServicioEnProceso): Serializable
data class VMValidarServicioEnProceso(
    val flgServicioEnProceso: Boolean,
    val idServicioEnProceso: Int): Serializable