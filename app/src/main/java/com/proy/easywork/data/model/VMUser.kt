package com.proy.easywork.data.model

import java.io.Serializable

data class VMUser (val nombres:String,
              val apellidos:String,
              val correo:String,
              val mostrarRegistroUsuario:String,
              val mostrarVerificacionCelular:String,
              val mostrarVerificiacionCorreo:String): Serializable {
    fun isShowRegister()  = mostrarRegistroUsuario.equals("true",true)
    fun isShowVerifyPhone()  = mostrarVerificacionCelular.equals("true",false)
    fun isShowVerifyEmail()  = mostrarVerificiacionCorreo.equals("true",false)
}