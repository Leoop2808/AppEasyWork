package com.proy.easywork.domain.repositories

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.db.entity.CategoriaServicio
import com.proy.easywork.data.db.entity.Distrito
import com.proy.easywork.data.exception.DefaultException
import com.proy.easywork.data.model.request.*
import com.proy.easywork.data.model.response.*
import com.proy.easywork.domain.BaseRepository
import com.proy.easywork.domain.MADataResult
import java.net.HttpURLConnection

class PrincipalRepository(aplication: Application) : BaseRepository(){
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    val database = db.getDatabase(aplication)
    suspend fun getCategorias(): MADataResult<List<CategoriaServicio>?>{
        return MADataResult.Success(database.masterDao().listCategoria())
    }

    suspend fun getDistritos(): MADataResult<List<Distrito>?>{
        return MADataResult.Success(database.masterDao().listDistrito())
    }

    suspend fun getCategoria(codCategoria:String): MADataResult<String?>{
        return MADataResult.Success(database.masterDao().getCategoria(codCategoria))
    }

    suspend fun buscarTecnicoGeneral(request: RQBuscarTecnicosGeneral): MADataResult<RSBuscarTecnicosGeneral> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.buscarTecnicosGeneral(sp.getToken(), request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body())
                }
                HttpURLConnection.HTTP_NO_CONTENT -> MADataResult.Failure(
                    Exception(DefaultException.MESSAGE_NO_CONTENT)
                )
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.UnAuthorized(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DefaultException.UNAUTHORIZED_ERROR else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty())DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                else -> MADataResult.Failure(
                    Exception(DefaultException.DEFAULT_MESSAGE)
                )
            }
        } catch (e: Exception) {
            var ex = e  
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

    suspend fun buscarTecnicoFavoritos(request: RQBuscarTecnicosGeneral): MADataResult<RSBuscarTecnicosGeneral> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.buscarTecnicosFavoritos(sp.getToken(), request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body())
                }
                HttpURLConnection.HTTP_NO_CONTENT -> MADataResult.Failure(
                    Exception(DefaultException.MESSAGE_NO_CONTENT)
                )
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.UnAuthorized(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DefaultException.UNAUTHORIZED_ERROR else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty())DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                else -> MADataResult.Failure(
                    Exception(DefaultException.DEFAULT_MESSAGE)
                )
            }
        } catch (e: Exception) {
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

    suspend fun getPerfilTecnico(request: RQObtenerPerfilTecnico): MADataResult<RSObtenerPerfilTecnico> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.obtenerPerfilTecnico(sp.getToken(), request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body())
                }
                HttpURLConnection.HTTP_NO_CONTENT -> MADataResult.Failure(
                    Exception(DefaultException.MESSAGE_NO_CONTENT)
                )
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.UnAuthorized(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DefaultException.UNAUTHORIZED_ERROR else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty())DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                else -> MADataResult.Failure(
                    Exception(DefaultException.DEFAULT_MESSAGE)
                )
            }
        } catch (e: Exception) {
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

    suspend fun solicitarServicio(request: RQSolicitarServicio): MADataResult<RSSolicitarServicio> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.solicitarServicio(sp.getToken(), request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body())
                }
                HttpURLConnection.HTTP_NO_CONTENT -> MADataResult.Failure(
                    Exception(DefaultException.MESSAGE_NO_CONTENT)
                )
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.UnAuthorized(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DefaultException.UNAUTHORIZED_ERROR else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty())DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                else -> MADataResult.Failure(
                    Exception(DefaultException.DEFAULT_MESSAGE)
                )
            }
        } catch (e: Exception) {
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

    suspend fun clienteValidarServicioEnProceso(): MADataResult<RSValidarServicioEnProceso> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.clienteValidarServicioEnProceso(sp.getToken())
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body())
                }
                HttpURLConnection.HTTP_NO_CONTENT -> MADataResult.Failure(
                    Exception(DefaultException.MESSAGE_NO_CONTENT)
                )
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.UnAuthorized(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DefaultException.UNAUTHORIZED_ERROR else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty())DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                else -> MADataResult.Failure(
                    Exception(DefaultException.DEFAULT_MESSAGE)
                )
            }
        } catch (e: Exception) {
            var ex = e
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }
    suspend fun getServicioEnProceso(idServicioEnProceso: Int): MADataResult<RSClienteObtenerServicioEnProceso> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.clienteObtenerServicioEnProceso(sp.getToken(), idServicioEnProceso)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body())
                }
                HttpURLConnection.HTTP_NO_CONTENT -> MADataResult.Failure(
                    Exception(DefaultException.MESSAGE_NO_CONTENT)
                )
                HttpURLConnection.HTTP_BAD_REQUEST -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_UNAUTHORIZED -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.UnAuthorized(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DefaultException.UNAUTHORIZED_ERROR else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_NOT_FOUND -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty())DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                HttpURLConnection.HTTP_INTERNAL_ERROR -> {
                    val errorResponse: RSErrorMessage? = gson.fromJson(result.errorBody()!!.charStream(), type)
                    MADataResult.Failure(Exception(if (errorResponse?.error_description.isNullOrEmpty()) DEFAULT_ERROR_MESSAGE else errorResponse?.error_description))
                }
                else -> MADataResult.Failure(
                    Exception(DefaultException.DEFAULT_MESSAGE)
                )
            }
        } catch (e: Exception) {
            var ex = e
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }
}