package com.proy.easywork.data.model.request

import java.io.Serializable

class RQDatosPersona (val correo: String, val celular: String, val nombre1: String,
                      val nombre2: String, val apellido2: String, val codTipoDocumento: String,
                      val documento: String, val genero: String, val latitud: Double,
                      val longitud: Double, val codDistrito: String): Serializable