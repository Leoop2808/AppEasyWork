package com.proy.easywork.data.model.response

import com.proy.easywork.data.model.VMCategoriaServicio
import com.proy.easywork.data.model.VMTipoDocumento
import com.proy.easywork.data.model.VMDistrito
import com.proy.easywork.data.model.VMMedioPago
import com.proy.easywork.data.model.VMTipoTransporte
import com.proy.easywork.data.model.VMTipoBusqueda
import java.io.Serializable

class RSMaestros (val listaCategoriaServicio : MutableList<VMCategoriaServicio> = mutableListOf(),
                  val listaTipoDocumento : MutableList<VMTipoDocumento> = mutableListOf(),
                  val listaDistrito : MutableList<VMDistrito> = mutableListOf(),
                  val listaMedioPago : MutableList<VMMedioPago> = mutableListOf(),
                  val listaTipoTransporte : MutableList<VMTipoTransporte> = mutableListOf(),
                  val listaTipoBusqueda : MutableList<VMTipoBusqueda> = mutableListOf()) : Serializable{
}