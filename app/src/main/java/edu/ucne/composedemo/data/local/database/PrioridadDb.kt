package edu.ucne.composedemo.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.composedemo.data.local.dao.PrioridadDao
import edu.ucne.composedemo.data.local.entities.prioridadesEntity

@Database(
    entities = [
        prioridadesEntity::class
    ],
    version = 1,
    exportSchema = false
)

abstract class PrioridadDb : RoomDatabase() {
    abstract fun prioridadDao(): PrioridadDao
}

/*Agregando la base de datos*/