package com.proy.easywork.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.proy.easywork.data.db.dao.masterDao
import com.proy.easywork.data.db.entity.*

@Database(entities = [CategoriaServicio::class, TipoDocumento::class,
    Distrito::class, MedioPago::class, TipoBusqueda::class,
    TipoTransporte::class], version = 1, exportSchema = false)
abstract class EasyWorkDatabase() : RoomDatabase(){
    abstract fun masterDao():masterDao
    companion object{
        @Volatile
        private var INSTANCE: EasyWorkDatabase? = null

        fun getDatabase(context: Context): EasyWorkDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){

                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EasyWorkDatabase::class.java,
                    "easywork_database_dev")

                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
