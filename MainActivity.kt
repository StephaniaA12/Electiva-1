package com.example.juegoproyecto

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoproyecto.adapter.TareaAdapter
import com.example.juegoproyecto.model.Tarea
import com.example.juegoproyecto.viewmodel.TareaViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: TareaAdapter
    private val vm: TareaViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rv = findViewById<RecyclerView>(R.id.rvTareas)
        val btnSync = findViewById<Button>(R.id.btnSync)
        val etTitulo = findViewById<EditText>(R.id.etTitulo)
        val btnAgregar = findViewById<Button>(R.id.btnAgregar)

        adapter = TareaAdapter(emptyList())
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter

        // 🔄 Sincronizar desde Google Sheets
        btnSync.setOnClickListener {
            vm.cargarTareas()
        }

        // ➕ Agregar nueva tarea local
        btnAgregar.setOnClickListener {
            val titulo = etTitulo.text.toString().trim()
            if (titulo.isNotEmpty()) {
                val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                val nueva = Tarea(
                    id = Random().nextInt(1000),
                    titulo = titulo,
                    descripcion = "Tarea agregada localmente",
                    fecha = fecha,
                    completed = false
                )
                vm.agregarTarea(nueva)
                etTitulo.text.clear()
            }
        }

        // 🔹 Observar cambios del ViewModel
        vm.tareas.observe(this) { lista ->
            adapter.actualizar(lista)
        }
    }
}
