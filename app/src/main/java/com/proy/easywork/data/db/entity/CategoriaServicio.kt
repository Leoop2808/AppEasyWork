package com.proy.easywork.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "categoria_servicio_table")
data class CategoriaServicio(@PrimaryKey(autoGenerate = true)
                            val id: Int,
                            val codCategoriaServicio:String,
                            val siglaCategoriaServicio:String,
                             val nombreImgCategoriaServicio:String)