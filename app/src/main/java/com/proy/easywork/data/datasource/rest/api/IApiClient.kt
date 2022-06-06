package com.proy.easywork.data.datasource.rest.api

import com.proy.easywork.data.model.VMAuthenticationDTO
import com.proy.easywork.data.model.request.*
import com.proy.easywork.data.model.response.RSBusquedaTecnico
import com.proy.easywork.data.model.response.RSMaestros
import com.proy.easywork.data.model.response.RSMessage
import com.proy.easywork.data.model.response.RSPerfilTecnico
import retrofit2.Response
import retrofit2.http.*

interface IApiClient {

    @FormUrlEncoded
    @POST("Token")
    suspend fun authentication(@Header("Authorization") auth:String,
                               @Header("rol") rol:String,
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
    suspend fun obtenerListaMaestros(
        @Header("Authorization") token: String): Response<RSMaestros?>


    @POST("api/usuario/autenticacion/envio-verificacion-celular")
    suspend fun enviarCodigoCelular(
        @Header("Authorization") token: String,
        @Body request: RQCodigoCelular
    ): Response<RSMessage>

    @POST("api/usuario/autenticacion/envio-verificacion-correo")
    suspend fun enviarCodigoCorreo(
        @Header("Authorization") token: String,
        @Body request: RQCodigoCorreo
    ): Response<RSMessage>

    @POST("api/usuario/autenticacion/verificar-codigo-verificacion-correo")
    suspend fun verificarCodigoCorreo(
        @Header("Authorization") token: String,
        @Body request: RQVerificarCodigo): Response<RSMessage>

    @POST("api/usuario/autenticacion/verificar-codigo-verificacion-celular")
    suspend fun verificarCodigoCelular(
        @Header("Authorization") token: String,
        @Body request: RQVerificarCodigoCelular): Response<RSMessage>


    @POST("api/usuario/envio-correo-codigo-recuperacion-clave")
    suspend fun enviarCorreoContrasena(
        @Header("Authorization") token: String,
        @Body request: RQCorreo
    ): Response<RSMessage>

    @PUT("api/usuario/actualizar-clave")
    suspend fun actualizarClave(
        @Header("Authorization") token: String,
        @Body request: RQClave
    ): Response<RSMessage>

    @POST("api/usuario/registrar-dispositivo")
    suspend fun registrarDispositivo(
        @Header("Authorization") token: String,
        @Body request: RQDispositivo
    ): Response<RSMessage>

    @POST("api/usuario/registro-usuario-cliente")
    suspend fun registrarUsuarioCliente(
        @Header("Authorization") token: String,
        @Body request: RQRegistrarUsuarioCliente
    ): Response<RSMessage>

    @POST("api/usuario/autenticacion/envio-autenticacion-celular")
    suspend fun enviarCodigoAutenticacionCelular(
        @Header("Authorization") token: String,
        @Body request: RQCodigoCelular
    ): Response<RSMessage>

    @POST("api/usuario/autenticacion/celular")
    suspend fun authenticationPhone(@Header("Authorization") auth:String,
                                    @Body registro: RQAuthenticationPhone):  Response<VMAuthenticationDTO>


    @GET("api/cliente/busqueda-tecnicos")
    suspend fun buscarTecnicos(@Header("Authorization") auth:String,
                               @Body request: RQBusqueda): Response<RSBusquedaTecnico>

    @GET("api/cliente/perfil-tecnico")
    suspend fun getPerfil(@Header("Authorization") auth:String,
                          @Body request: RQPerfil): Response<RSPerfilTecnico>

    @POST("api/usuario/autenticacion/tecnico/google")
    suspend fun authenticationGoogleTecnico(@Header("Authorization") auth:String,
                                     @Body registro: RQAuthenticationGoogle):  Response<VMAuthenticationDTO>

    @POST("api/usuario/autenticacion/tecnico/facebook")
    suspend fun authenticationFacebookTecnico(@Header("Authorization") auth:String,
                                       @Body registro: RQAuthenticationFacebook):  Response<VMAuthenticationDTO>

    @POST("api/usuario/autenticacion/tecnico/envio-verificacion-celular")
    suspend fun enviarCodigoCelularTecnico(
        @Header("Authorization") token: String,
        @Body request: RQCodigoCelular
    ): Response<RSMessage>

    @POST("api/usuario/autenticacion/tecnico/envio-verificacion-correo")
    suspend fun enviarCodigoCorreoTecnico(
        @Header("Authorization") token: String,
        @Body request: RQCodigoCorreo
    ): Response<RSMessage>

    @POST("api/usuario/autenticacion/tecnico/verificar-codigo-verificacion-correo")
    suspend fun verificarCodigoCorreoTecnico(
        @Header("Authorization") token: String,
        @Body request: RQVerificarCodigo): Response<RSMessage>

    @POST("api/usuario/autenticacion/tecnico/verificar-codigo-verificacion-celular")
    suspend fun verificarCodigoCelularTecnico(
        @Header("Authorization") token: String,
        @Body request: RQVerificarCodigoCelular): Response<RSMessage>


    @POST("api/usuario/tecnico/envio-correo-codigo-recuperacion-clave")
    suspend fun enviarCorreoContrasenaTecnico(
        @Header("Authorization") token: String,
        @Body request: RQCorreo
    ): Response<RSMessage>

    @PUT("api/usuario/tecnico/actualizar-clave")
    suspend fun actualizarClaveTecnico(
        @Header("Authorization") token: String,
        @Body request: RQClave
    ): Response<RSMessage>

    @POST("api/usuario/tecnico/autenticacion/envio-autenticacion-celular")
    suspend fun enviarCodigoAutenticacionCelularTecnico(
        @Header("Authorization") token: String,
        @Body request: RQCodigoCelular
    ): Response<RSMessage>

    @POST("api/usuario/tecnico/autenticacion/celular")
    suspend fun authenticationPhoneTecnico(@Header("Authorization") auth:String,
                                    @Body registro: RQAuthenticationPhone):  Response<VMAuthenticationDTO>
}