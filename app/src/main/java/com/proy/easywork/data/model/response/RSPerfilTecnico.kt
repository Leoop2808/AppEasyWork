package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSPerfilTecnico (val Message: String, val data: VMPerfil):Serializable

data class VMPerfil(val idPerfilTrabajador: Int,
                    val codUsuarioTecnico: String,
                    val nombreTecnico:String,
                    val categoria: String,
                    val codUsuarioTecnicoCategoria: String,
                    val codCategoria: String,
                    val cantidadClientes: String,
                    val flgCorazon: Boolean,
                    val datosValoracion: VMValoracion,
                    val cantidadReseñas: String,
                    val listaComentarios: MutableList<VMcomentario> = mutableListOf()): Serializable

data class VMValoracion(val cantidad5Estrellas: Int,
                        val cantidad4Estrellas: Int,
                        val cantidad3Estrellas: Int,
                        val cantidad2Estrellas: Int,
                        val cantidad1Estrellas: Int,
                        val promedioEstrellas: Int): Serializable

data class VMcomentario(val cantEstrellas: Int,
                        val nombreUsuario: String,
                        val fechaComentario: String,
                        val flgServicioVerificado: Boolean,
                        val comentario: String): Serializable