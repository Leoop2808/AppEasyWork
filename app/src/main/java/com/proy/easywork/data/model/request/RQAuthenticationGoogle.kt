package com.proy.easywork.data.model.request

import java.io.Serializable

class RQAuthenticationGoogle (
    val google_token : String,
    val latitud : Double,
    val longitud : Double) : Serializable {}