package edu.ucne.composedemo.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object PrioridadList : Screen()
    @Serializable
    data class Prioridad(val prioridadId: Int) : Screen()
}