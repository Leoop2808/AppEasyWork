package com.proy.easywork.data.model

import java.io.Serializable

data class VMAuthenticationDTO(val access_token:String?="", val token_type:String?="",val expires_in:Long?=-1, val nombres:String?="",
                               val apellidos:String?="",val correo:String?="",val flgMostrarRegistroUsuario:String?="",
                               val flgCelularValidado:String?="",val flgCorreoValidado:String?="",val rol:String?=""):
    Serializable {
    constructor() : this("", "", -1, "","","","")
    fun toAuthentication() = VMAuthentication(access_token?:"",token_type?:"",
        expires_in?:-1,nombres?:"",apellidos?:"",correo?:"",
        flgMostrarRegistroUsuario?:"",flgCelularValidado?:"",flgCorreoValidado?:"", rol?:"")
}

