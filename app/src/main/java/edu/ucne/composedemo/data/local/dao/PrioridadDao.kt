package edu.ucne.composedemo.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.composedemo.data.local.entities.prioridadesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PrioridadDao {
    @Upsert()
    suspend fun save(prioridad: prioridadesEntity)

    @Query(
        """
        SELECT * 
        FROM Prioridades 
        WHERE prioridadId=:id  
        LIMIT 1
        """
    )
    suspend fun find(id: Int): prioridadesEntity?

    @Delete
    suspend fun delete(aporte: prioridadesEntity)

    @Query("SELECT * FROM Prioridades")
    fun getAll(): Flow<List<prioridadesEntity>>

    @Query("SELECT diasCompromiso FROM Prioridades")
    suspend fun getAllDias(): List<Int>

}