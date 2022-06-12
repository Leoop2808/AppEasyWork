package com.proy.easywork.data.model.response

import java.io.Serializable

data class RSObtenerPerfilTecnico (val Message: String, val data: VMPerfilTecnico): Serializable

data class VMPerfilTecnico(
                    val urlImagenTecnico: String,
                    val idPerfilTrabajador: Int,
                    val codUsuarioTecnico: String,
                    val nombreTecnico:String,
                    val categoria: String,
                    val idTecnicoCategoriaServicio: Int,
                    val codCategoria: String,
                    val cantidadClientes: String,
                    val flgCorazon: Boolean,
                    val datosValoracion: VMTecnicoValoracion,
                    val cantidadResenias: String,
                    val listaComentarios: MutableList<VMPerfilComentario> = mutableListOf()): Serializable

data class VMTecnicoValoracion(val cantidad5Estrellas: Int,
                        val cantidad4Estrellas: Int,
                        val cantidad3Estrellas: Int,
                        val cantidad2Estrellas: Int,
                        val cantidad1Estrellas: Int,
                        val promedioEstrellas: Int): Serializable

data class VMPerfilComentario(val cantEstrellas: Int,
                        val nombreUsuario: String,
                        val fechaComentario: String,
                        val flgServicioVerificado: Boolean,
                        val comentario: String): Serializable