package com.proy.easywork.data.datasource.preferences

import com.proy.easywork.data.model.VMUser


interface MSharedPreferences {
    fun saveSession(user: VMUser)
    fun isSession():Boolean
    fun session():VMUser?
    fun clearSession()
    fun saveToken(token: String)
    fun getToken():String
    fun saveTok(tok: String)
    fun getTok():String
    fun saveTokenFCM(tokenFCM: String)
    fun getTokenFCM():String

    fun saveRol(rol: String)
    fun getRol():String

}