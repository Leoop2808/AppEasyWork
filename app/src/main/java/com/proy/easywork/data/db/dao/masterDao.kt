package com.proy.easywork.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.proy.easywork.data.db.entity.CategoriaServicio

@Dao
interface masterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addCategoriaServicio(categoriaServicio: CategoriaServicio): Long

    @Query("DELETE FROM categoria_servicio_table")
    fun deleteCategoria()

    @Query("SELECT * FROM categoria_servicio_table")
    fun listCategoria(): List<CategoriaServicio?>?
}