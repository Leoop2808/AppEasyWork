package com.proy.easywork.data.datasource.storage

import android.content.Context
import androidx.annotation.NonNull
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.preferences.MSharedPreferences


object MDataInjection : MStorageInjection {

    private var preferences: MSharedPreferences? = null
    val instance = this

    override fun setUp(@NonNull app: Context) {
        preferences = MDefaultSharedPref(app)
    }

    fun providePreferences() =
        if (preferences == null) throw Exception("MSharedPreferences not initialized ") else preferences!!

    override fun destroy() {
        preferences = null
    }
}