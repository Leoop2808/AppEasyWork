package com.proy.easywork.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipo_busqueda_table")
data class TipoBusqueda(@PrimaryKey(autoGenerate = true)
                          val id: Int,
                          val codTipoBusqueda:String,
                          val siglaTipoBusqueda:String)