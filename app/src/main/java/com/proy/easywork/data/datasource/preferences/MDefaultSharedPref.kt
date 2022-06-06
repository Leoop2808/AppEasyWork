package com.proy.easywork.data.datasource.preferences

import android.content.Context
import com.proy.easywork.data.datasource.helpers.GsonHelper.convertJsonToClass
import com.proy.easywork.data.datasource.helpers.GsonHelper.generateJSONObject
import com.proy.easywork.data.model.VMUser

class MDefaultSharedPref(context: Context): MSharedPreferences {
    companion object {
        private var appId = "easywork_preferences"
        private var appSettings = "easywork_preferences_settings"
        const val SESSION = ".session.easywork.user"

        const val TOK = ".session.easywork.tok"
        const val TOKEN = ".session.easywork.token"
        const val TOKEN_FCM = ".session.easywork.token.fcm"
        const val ROL = ".session.easywork.rol"
    }

    private val preferences by lazy {
        context.getSharedPreferences(appId, Context.MODE_PRIVATE)
    }

    private val editor by lazy {
        preferences.edit()
    }

    private val preferencesSetting by lazy {
        context.getSharedPreferences(appSettings, Context.MODE_PRIVATE)
    }

    private val editorSetting by lazy {
        preferencesSetting.edit()
    }

    override fun saveSession(user: VMUser) {
        editor.putString(keySession(), user.generateJSONObject().toString())
            .apply()
    }

    override fun isSession(): Boolean {
        return preferences.contains(keySession())
    }

    override fun session(): VMUser {
        val jsonSession: String = preferences.getString(keySession(), "") ?: ""
        return jsonSession.convertJsonToClass(VMUser::class.java)
    }


    override fun clearSession() {
        editor.clear()
            .apply()
    }

    private fun keySession():String{
        return appId+ SESSION
    }


    private fun keyToken():String{
        return appId+ TOKEN
    }

    private fun keyRol():String{
        return appId+ ROL
    }

    override fun saveToken(token: String) {
        editor.putString(keyToken(), token).apply()
    }

    override fun getToken():String {
        return preferences.getString(keyToken(), "") ?: ""
    }

    private fun keyTok():String{
        return appId+ TOK
    }
    override fun saveTok(tok: String) {
        editor.putString(keyTok(), tok).apply()
    }

    override fun getTok(): String {
        return preferences.getString(keyTok(), "") ?: ""
    }

    private fun keyTokenFCM():String{
        return appId+ TOKEN_FCM
    }

    override fun saveTokenFCM(tokenFCM: String) {
        editor.putString(keyTokenFCM(), tokenFCM).apply()
    }

    override fun getTokenFCM(): String {
        return preferences.getString(keyTokenFCM(), "") ?: ""
    }

    override fun saveRol(rol: String) {
        editor.putString(keyRol(), rol).apply()
    }

    override fun getRol(): String {
        return preferences.getString(keyRol(), "") ?: ""
    }
}