package com.proy.easywork.data.exception

class DefaultException (override var message: String?= DEFAULT_MESSAGE) : Exception(message) {

    companion object {
        const val DEFAULT_MESSAGE="Servidor no disponible"
        const val UNAUTHORIZED_ERROR = "Su sesión ha expirado"
    }


    init {
        if(message==null) message= DEFAULT_MESSAGE
    }
}