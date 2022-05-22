package com.proy.easywork.data.model.request

import java.io.Serializable

class RQAuthenticationPhone (
    val nroCelular : String,
    val codVerificacion : String,
    val latitud : Double,
    val longitud : Double) : Serializable {}