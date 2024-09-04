package edu.ucne.composedemo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.data.local.dao.PrioridadDao
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import android.content.Context
import androidx.room.Room

@Database(
    entities = [PrioridadEntity::class],
    version = 1,
    exportSchema = false
)
abstract class PrioridadDb : RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao

    companion object {
        @Volatile
        private var INSTANCE: PrioridadDb? = null

        fun getDatabase(context: Context): PrioridadDb {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PrioridadDb::class.java,
                    "prioridades_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}



/*Agregando la base de datos*/