package com.proy.easywork.data.exception

class DefaultException (override var message: String?= DEFAULT_MESSAGE) : Exception(message) {

    companion object {
        const val DEFAULT_MESSAGE="Servidor no disponible"
        const val UNAUTHORIZED_ERROR = "Su sesión ha expirado"
        const val ACCOUNT_AUTHENTICATE_GENERIC_ERROR = "No se ha podido autenticar"
        const val ACCOUNT_BAD_REQUEST_ERROR = "Usuario y/o contraseña incorrectos"
        const val ACCOUNT_BAD_EMAIL = "Correo electrónico ya esta registrado"
        const val REGISTRATION_REQUEST_ERROR = "Registro incorrecto"
    }


    init {
        if(message==null) message= DEFAULT_MESSAGE
    }
}