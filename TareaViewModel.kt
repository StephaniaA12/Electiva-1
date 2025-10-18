package com.example.juegoproyecto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.juegoproyecto.database.AppDatabase
import com.example.juegoproyecto.model.Tarea
import com.example.juegoproyecto.repository.TareaRepository
import kotlinx.coroutines.launch

class TareaViewModel(application: Application) : AndroidViewModel(application) {

    //Instancia del repositorio (Room + Retrofit)
    private val repository: TareaRepository

    //LiveData expone la lista de tareas
    private val _tareas = MutableLiveData<List<Tarea>>(emptyList())
    val tareas: LiveData<List<Tarea>> = _tareas

    init {
        val db = AppDatabase.getDatabase(application)
        repository = TareaRepository(db.tareaDao(), application.applicationContext)

        cargarTareas()
    }

    fun cargarTareas() {
        viewModelScope.launch {
            val lista = repository.obtenerTareas()
            _tareas.postValue(lista)
        }
    }

    fun agregarTarea(t: Tarea) {
        viewModelScope.launch {
            repository.agregarTarea(t)
            _tareas.postValue(repository.obtenerTareas())
        }
    }
}

