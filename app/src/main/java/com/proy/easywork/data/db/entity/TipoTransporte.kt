package com.proy.easywork.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipo_transporte_table")
data class TipoTransporte(@PrimaryKey(autoGenerate = true)
                     val id: Int,
                     val codTipoTransporte:String,
                     val siglaTipoTransporte:String)