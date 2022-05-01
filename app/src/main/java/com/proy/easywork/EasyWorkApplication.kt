package com.proy.easywork

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.datasource.storage.MStorageInjection

class EasyWorkApplication: Application() {

    lateinit var mStorageInjection: MStorageInjection

    override fun onCreate() {
        super.onCreate()
        mStorageInjection=  MDataInjection.instance
        mStorageInjection.setUp(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}