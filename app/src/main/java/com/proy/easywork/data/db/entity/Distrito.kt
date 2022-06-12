package com.proy.easywork.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "distrito_table")
data class Distrito(@PrimaryKey(autoGenerate = true)
                    val id: Int,
                    var codDistrito:String,
                    val siglaDistrito:String)