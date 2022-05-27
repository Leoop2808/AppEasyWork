package com.proy.easywork.data.model

import java.io.Serializable

data class VMAuthentication(val access_token:String, val token_type:String,val expires_in:Long, val nombres:String,
                            val apellidos:String,val correo:String,val flgMostrarRegistroUsuario:String,
                            val flgCelularValidado:String,val flgCorreoValidado:String):
    Serializable {
    constructor() : this("", "", -1, "","","","","","")
    fun toUser() = VMUser(nombres,apellidos,correo,flgMostrarRegistroUsuario, flgCelularValidado, flgCorreoValidado, "1")
    fun getAuthenticationToken():String{
        return  if (token_type.isNotEmpty()){
            "$token_type $access_token"
        }else{
            "bearer $access_token"
        }
    }
}