package com.example.juegoproyecto.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tareas")
data class Tarea(
    @PrimaryKey val id: Int,
    val titulo: String,
    val descripcion: String,
    val fecha: String,
    val completed: Boolean = false
)
