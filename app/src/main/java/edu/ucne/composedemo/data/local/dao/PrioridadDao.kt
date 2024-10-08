package edu.ucne.composedemo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.data.local.entities.PrioridadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao {
    @Upsert
    suspend fun save(prioridad: PrioridadEntity) : Long

    @Query(
        """
        SELECT * 
        FROM Prioridades 
        WHERE prioridadId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): PrioridadEntity?

    @Delete
    suspend fun delete(aporte: PrioridadEntity)

    @Query("SELECT * FROM Prioridades")
    fun getAll(): Flow<List<PrioridadEntity>>

    @Query("SELECT diasCompromiso FROM Prioridades")
    suspend fun getAllDias(): List<Int>



}



/*Agregando la Interfaz*/