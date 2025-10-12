package com.example.juegoproyecto.database

import androidx.room.*
import com.example.juegoproyecto.model.Tarea

@Dao
interface TareaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(tarea: Tarea)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertarTodas(lista: List<Tarea>)

    @Query("SELECT * FROM tareas ORDER BY fecha DESC")
    suspend fun obtenerTareas(): List<Tarea>

    @Delete
    suspend fun eliminar(tarea: Tarea)

    @Query("DELETE FROM tareas")
    suspend fun eliminarTodas()
}
