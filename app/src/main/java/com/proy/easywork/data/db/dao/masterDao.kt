package com.proy.easywork.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.proy.easywork.data.db.entity.*

@Dao
interface masterDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addCategoriaServicio(categoriaServicio: CategoriaServicio): Long
    @Query("DELETE FROM categoria_servicio_table")
    fun deleteCategoria()
    @Query("SELECT * FROM categoria_servicio_table order by codCategoriaServicio")
    fun listCategoria(): List<CategoriaServicio>?
    @Query("SELECT siglaCategoriaServicio FROM categoria_servicio_table WHERE codCategoriaServicio = :codCategoria")
    fun getCategoria(codCategoria : String): String?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addDistrito(distrito: Distrito): Long
    @Query("DELETE FROM distrito_table")
    fun deleteDistrito()
    @Query("SELECT * FROM distrito_table")
    fun listDistrito(): List<Distrito>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMedioPago(medioPago: MedioPago): Long
    @Query("DELETE FROM medio_pago_table")
    fun deleteMedioPago()
    @Query("SELECT * FROM medio_pago_table")
    fun listMedioPago(): List<MedioPago?>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTipoBusqueda(tipoBusqueda: TipoBusqueda): Long
    @Query("DELETE FROM tipo_busqueda_table")
    fun deleteTipoBusqueda()
    @Query("SELECT * FROM tipo_busqueda_table")
    fun listTipoBusqueda(): List<TipoBusqueda?>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTipoDocumento(tipoDocumento: TipoDocumento): Long
    @Query("DELETE FROM tipo_documento_table")
    fun deleteTipoDocumento()
    @Query("SELECT * FROM tipo_documento_table")
    fun listTipoDocumento(): List<TipoDocumento?>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTipoTransporte(tipoTransporte: TipoTransporte): Long
    @Query("DELETE FROM tipo_transporte_table")
    fun deleteTipoTransporte()
    @Query("SELECT * FROM tipo_transporte_table")
    fun listTipoTransporte(): List<TipoTransporte?>?
}