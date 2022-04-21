package com.proy.easywork.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tipo_documento_table")
data class TipoDocumento (@PrimaryKey(autoGenerate = true)
                          val id: Int,
                          val codTipoDocumento: String,
                          val siglaTipoDocumento:String)