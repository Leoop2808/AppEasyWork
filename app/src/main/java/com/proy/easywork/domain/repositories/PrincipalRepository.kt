package com.proy.easywork.domain.repositories

import android.app.Application
import com.proy.easywork.data.datasource.preferences.MDefaultSharedPref
import com.proy.easywork.data.datasource.storage.MDataInjection
import com.proy.easywork.data.db.entity.CategoriaServicio
import com.proy.easywork.domain.BaseRepository
import com.proy.easywork.domain.MADataResult

class PrincipalRepository(aplication: Application) : BaseRepository(){
    val sp: MDefaultSharedPref = MDataInjection.instance.providePreferences() as MDefaultSharedPref
    val database = db.getDatabase(aplication)
    suspend fun getCategorias(): MADataResult<List<CategoriaServicio>?>{
        return MADataResult.Success(database.masterDao().listCategoria())
    }
}