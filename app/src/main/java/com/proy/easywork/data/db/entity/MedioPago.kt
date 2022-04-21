package com.proy.easywork.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "medio_pago_table")
data class MedioPago(@PrimaryKey(autoGenerate = true)
                    val id: Int,
                    val codMedioPago:String,
                    val siglaMedioPago:String)