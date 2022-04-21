package com.proy.easywork.domain.repositories


import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.exception.DefaultException
import com.proy.easywork.data.model.VMAuthentication
import com.proy.easywork.data.model.response.RSMaestros
import com.proy.easywork.domain.BaseRepository
import com.proy.easywork.domain.MADataResult
import java.net.HttpURLConnection

class LoginRepository: BaseRepository() {
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref

    suspend fun listarMaestros(): MADataResult<RSMaestros?> {

        return try {
            val result= mRemoteClient?.obtenerListaMaestros(sp.getToken())
            when (result?.code()) {
                HttpURLConnection.HTTP_OK -> MADataResult.Success(result.body())

                HttpURLConnection.HTTP_BAD_REQUEST ->MADataResult.UnAuthorized(Exception(DefaultException.UNAUTHORIZED_ERROR))
                HttpURLConnection.HTTP_INTERNAL_ERROR ->MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
                else -> MADataResult.Failure(Exception(DefaultException.REGISTRATION_REQUEST_ERROR))
            }
        } catch (e: Exception) {
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

    suspend fun authenticate(user:String,password:String): MADataResult<VMAuthentication> {
        return try {
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
                HttpURLConnection.HTTP_BAD_REQUEST -> MADataResult.AccountFailure(Exception(DefaultException.ACCOUNT_BAD_REQUEST_ERROR))
                HttpURLConnection.HTTP_INTERNAL_ERROR -> MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
                else -> MADataResult.AuthentificateFailure(Exception(DefaultException.ACCOUNT_AUTHENTICATE_GENERIC_ERROR))
            }
        } catch (e: Exception) {
            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
        }
    }

//    suspend fun authenticateGoogle(googleToken:String): MADataResult<VMAuthentication> {
//        return try {
//            val result = mRemoteClient?.authenticationGoogle(basicAuthentication,googleToken)
//            when (result?.code()) {
//                HttpURLConnection.HTTP_OK ->{
//                    result.body()?.let {
//                        val authentication = it.toAuthentication()
//                        preferences.clearSession()
//                        preferences.saveToken(authentication.getAuthenticationToken())
//                        preferences.saveSession(authentication.toUser())
//                    }
//                    MADataResult.Success(result.body()?.toAuthentication())
//                }
//                HttpURLConnection.HTTP_BAD_REQUEST -> MADataResult.AccountFailure(Exception(DefaultException.ACCOUNT_BAD_REQUEST_ERROR))
//                HttpURLConnection.HTTP_INTERNAL_ERROR -> MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
//                else -> MADataResult.AuthentificateFailure(Exception(DefaultException.ACCOUNT_AUTHENTICATE_GENERIC_ERROR))
//            }
//        } catch (e: Exception) {
//            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
//        }
//    }
//
//    suspend fun authenticateFacebook(facebookToken:String): MADataResult<VMAuthentication> {
//        return try {
//            val result = mRemoteClient?.authenticationFacebook(basicAuthentication,facebookToken)
//            when (result?.code()) {
//                HttpURLConnection.HTTP_OK ->{
//                    result.body()?.let {
//                        val authentication = it.toAuthentication()
//                        preferences.clearSession()
//                        preferences.saveToken(authentication.getAuthenticationToken())
//                        preferences.saveSession(authentication.toUser())
//                    }
//                    MADataResult.Success(result.body()?.toAuthentication())
//                }
//                HttpURLConnection.HTTP_BAD_REQUEST -> MADataResult.AccountFailure(Exception(DefaultException.ACCOUNT_BAD_REQUEST_ERROR))
//                HttpURLConnection.HTTP_INTERNAL_ERROR -> MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
//                else -> MADataResult.AuthentificateFailure(Exception(DefaultException.ACCOUNT_AUTHENTICATE_GENERIC_ERROR))
//            }
//        } catch (e: Exception) {
//            MADataResult.ServerFailure(Exception(DEFAULT_ERROR_MESSAGE))
//        }
//    }
}