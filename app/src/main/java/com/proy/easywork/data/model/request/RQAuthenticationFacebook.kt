package com.proy.easywork.data.model.request

import java.io.Serializable

class RQAuthenticationFacebook (
    val facebook_token : String,
    val latitud : Double,
    val longitud : Double) : Serializable {}