package com.proy.easywork.domain.repositories

import android.app.Application
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.exception.DefaultException
import com.proy.easywork.data.model.response.RSErrorMessage
import com.proy.easywork.data.model.response.RSObtenerSolicitudes
import com.proy.easywork.data.model.response.RSValidarServicioEnProceso
import com.proy.easywork.domain.BaseRepository
import com.proy.easywork.domain.MADataResult
import java.net.HttpURLConnection

class TecnicoRepository (aplication: Application) : BaseRepository(){
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    val database = db.getDatabase(aplication)

    suspend fun tecnicoValidarServicioEnProceso(): MADataResult<RSValidarServicioEnProceso> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.tecnicoValidarServicioEnProceso(sp.getToken())
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

    suspend fun obtenerSolicitudes(): MADataResult<RSObtenerSolicitudes> {
        return try {
            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.obtenerSolicitudes(sp.getToken())
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