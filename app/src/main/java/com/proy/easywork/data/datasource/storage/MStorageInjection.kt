package com.proy.easywork.data.datasource.storage

import android.content.Context

interface MStorageInjection {
    fun setUp(app: Context)
    fun destroy()
}