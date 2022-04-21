package com.proy.easywork.data.datasource.rest.api

import com.proy.easywork.data.model.VMAuthenticationDTO
import com.proy.easywork.data.model.request.AuthenticateResponse
import com.proy.easywork.data.model.request.RQAuthenticationFacebook
import com.proy.easywork.data.model.request.RQAuthenticationGoogle
import com.proy.easywork.data.model.response.RSMaestros
import retrofit2.Response
import retrofit2.http.*

interface IApiClient {

    @FormUrlEncoded
    @POST("Token")
    suspend fun authentication(@Header("Authorization") auth:String,
                               @Field("username") user: String,
                               @Field("password") password:String,
                               @Field("grant_type") grant_type:String="password"): Response<VMAuthenticationDTO>

    @POST("api/usuario/autenticacion/google")
    suspend fun authenticationGoogle(@Header("Authorization") auth:String,
                                     @Body registro: RQAuthenticationGoogle):  Response<VMAuthenticationDTO>

    @POST("api/usuario/autenticacion/facebook")
    suspend fun authenticationFacebook(@Header("Authorization") auth:String,
                                       @Body registro: RQAuthenticationFacebook):  Response<VMAuthenticationDTO>

    @GET("api/cliente/lista-maestros")
    suspend fun obtenerListaMaestros(@Header("Authorization") token: String): Response<RSMaestros?>
}