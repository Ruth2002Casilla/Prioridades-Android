package edu.ucne.composedemo.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prioridades")
data class PrioridadEntity(
    @PrimaryKey
    val prioridadId: Int? = null,
    var descripcion: String? = "",
    var diasCompromiso: Int? = 0
)

/*Entidad*/