package com.proy.easywork.data.datasource.rest.api

import com.proy.easywork.data.model.request.AuthenticateResponse
import retrofit2.Response
import retrofit2.http.*

interface IApiClient {

    @FormUrlEncoded
    @POST("token")
    @Headers("Authorization: Basic YXBwX21wYXBwXzIwMjI6ZHBQaW80ODI3MyQvZmtU")
    suspend fun authenticate(
        @Field("grant_type") grantType: String,
        @Field("username") userName: String,
        @Field("password") password: String,
        @Field("country") country: String
    ): Response<AuthenticateResponse>

}