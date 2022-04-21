package com.proy.easywork.domain

import com.proy.easywork.BuildConfig
import com.proy.easywork.data.datasource.rest.api.ApiClient
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.db.database.EasyWorkDatabase
import com.proy.easywork.data.exception.DefaultException
import okhttp3.Credentials

open class BaseRepository {
    // protected val DEFAULT_ERROR_MESSAGE = DefaultException.DEFAULT_MESSAGE
    protected var basicAuthentication = Credentials.basic(BuildConfig.ServerUser, BuildConfig.ServerPassword)
    protected val DEFAULT_ERROR_MESSAGE = DefaultException.DEFAULT_MESSAGE

    protected val mRemoteClient = ApiClient().getApiClient()
    protected  val db = EasyWorkDatabase
    protected val preferences by lazy {
        MDataInjection.instance.providePreferences()
    }
}