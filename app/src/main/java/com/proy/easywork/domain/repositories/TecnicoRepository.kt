package com.proy.easywork.domain.repositories

import android.app.Application
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.domain.BaseRepository

class TecnicoRepository (aplication: Application) : BaseRepository(){
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    val database = db.getDatabase(aplication)
}