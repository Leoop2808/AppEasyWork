package com.proy.easywork.domain.repositories


import android.app.Application
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.db.entity.*
import com.proy.easywork.data.exception.DefaultException
import com.proy.easywork.data.model.VMAuthentication
import com.proy.easywork.data.model.request.*
import com.proy.easywork.data.model.response.RSErrorMessage
import com.proy.easywork.data.model.response.RSMaestros
import com.proy.easywork.domain.BaseRepository
import com.proy.easywork.domain.MADataResult
import java.net.HttpURLConnection

class LoginRepository(aplication: Application): BaseRepository() {
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    val database = db.getDatabase(aplication)
    suspend fun listarMaestros(): MADataResult<RSMaestros?> {
        val gson = Gson()
        val type = object : TypeToken<RSErrorMessage>() {}.type
        return try {
            val result= mRemoteClient?.obtenerListaMaestros(sp.getToken())
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    database.masterDao().deleteCategoria()
                    val categorias = result.body()?.data?.listaCategoriaServicio
                    Log.e("Maestros", categorias?.size.toString())
                    categorias?.forEach {
                        Log.e("Maestros", it.nombreImgCategoriaServicio)
                        database.masterDao().addCategoriaServicio(CategoriaServicio(0,it.codCategoriaServicio, it.siglaCategoriaServicio, it.nombreImgCategoriaServicio))
                    }

                    database.masterDao().deleteDistrito()
                    val distritos = result.body()?.data?.listaDistrito
                    distritos?.forEach {
                        database.masterDao().addDistrito(Distrito(0, it.codDistrito, it.siglaDistrito))
                    }

                    database.masterDao().deleteMedioPago()
                    val medios = result.body()?.data?.listaMedioPago
                    medios?.forEach {
                        database.masterDao().addMedioPago(MedioPago(0, it.codMedioPago, it.siglaMedioPago))
                    }

                    database.masterDao().deleteTipoBusqueda()
                    val tiposBusq = result.body()?.data?.listaTipoBusqueda
                    tiposBusq?.forEach {
                        database.masterDao().addTipoBusqueda(TipoBusqueda(0, it.codTipoBusqueda, it.siglaTipoBusqueda))
                    }

                    database.masterDao().deleteTipoDocumento()
                    val tiposDoc = result.body()?.data?.listaTipoDocumento
                    tiposDoc?.forEach {
                        database.masterDao().addTipoDocumento(TipoDocumento(0,it.codTipoDocumento, it.siglaTipoDocumento))
                    }

                    database.masterDao().deleteTipoTransporte()
                    val tiposTrans = result.body()?.data?.listaTipoTransporte
                    tiposTrans?.forEach {
                        database.masterDao().addTipoTransporte(TipoTransporte(0,it.codTipoTransporte, it.siglaTipoTransporte))
                    }

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

    suspend fun authenticate(user:String,password:String): MADataResult<VMAuthentication> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.authentication(basicAuthentication,user,password)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    result.body()?.let {
                        val authentication = it.toAuthentication()
                        preferences.clearSession()
                        preferences.saveToken(authentication.getAuthenticationToken())
                        preferences.saveSession(authentication.toUser())
                    }
                    MADataResult.Success(result.body()?.toAuthentication())
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
                else -> MADataResult.AuthentificateFailure(Exception(DefaultException.ACCOUNT_AUTHENTICATE_GENERIC_ERROR))
            }
        } catch (e: Exception) {
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

    suspend fun authenticateGoogle(request : RQAuthenticationGoogle): MADataResult<VMAuthentication> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.authenticationGoogle(basicAuthentication,request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    result.body()?.let {
                        val authentication = it.toAuthentication()
                        preferences.clearSession()
                        preferences.saveToken(authentication.getAuthenticationToken())
                        preferences.saveSession(authentication.toUser())
                    }
                    MADataResult.Success(result.body()?.toAuthentication())
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

    suspend fun authenticateFacebook(request : RQAuthenticationFacebook): MADataResult<VMAuthentication> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.authenticationFacebook(basicAuthentication,request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    result.body()?.let {
                        val authentication = it.toAuthentication()
                        preferences.clearSession()
                        preferences.saveToken(authentication.getAuthenticationToken())
                        preferences.saveSession(authentication.toUser())
                    }
                    MADataResult.Success(result.body()?.toAuthentication())
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


    suspend fun enviarCodigoCelular(request : RQCodigoCelular): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.enviarCodigoCelular(basicAuthentication, request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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

    suspend fun enviarCodigoCorreo(request : RQCodigoCorreo): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.enviarCodigoCorreo(basicAuthentication, request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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

    suspend fun verificarCodigoCorreo(request : RQVerificarCodigo): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.verificarCodigoCorreo(basicAuthentication, request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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

    suspend fun verificarCodigoCelular(request : RQVerificarCodigoCelular): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.verificarCodigoCelular(basicAuthentication, request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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


    suspend fun actualizarClave(request : RQClave): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.actualizarClave(basicAuthentication, request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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


    suspend fun registrarDispositivo(request : RQDispositivo): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.registrarDispositivo(sp.getToken(), request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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

    suspend fun enviarCodigoAutenticacionCelular(request : RQCodigoCelular): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.enviarCodigoAutenticacionCelular(basicAuthentication, request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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

    suspend fun authenticatePhone(request : RQAuthenticationPhone): MADataResult<VMAuthentication> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.authenticationPhone(basicAuthentication,request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    result.body()?.let {
                        val authentication = it.toAuthentication()
                        preferences.clearSession()
                        preferences.saveToken(authentication.getAuthenticationToken())
                        preferences.saveSession(authentication.toUser())
                    }
                    MADataResult.Success(result.body()?.toAuthentication())
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

    suspend fun registrarUsuarioCliente(request : RQRegistrarUsuarioCliente): MADataResult<String> {
        return try {

            val gson = Gson()
            val type = object : TypeToken<RSErrorMessage>() {}.type
            val result = mRemoteClient?.registrarUsuarioCliente(basicAuthentication, request)
            when (result?.code()) {
                HttpURLConnection.HTTP_OK ->{
                    MADataResult.Success(result.body()?.Message)
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
}