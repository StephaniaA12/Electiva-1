package com.example.juegoproyecto.ui

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.EditText
import com.example.juegoproyecto.R

object AddTareaDialog {
    fun show(context: Context, onAdd: (titulo: String, descripcion: String) -> Unit) {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_add_tarea, null)
        val etTitulo = view.findViewById<EditText>(R.id.etTitulo)
        val etDesc = view.findViewById<EditText>(R.id.etDescripcion)

        AlertDialog.Builder(context)
            .setTitle("Agregar tarea")
            .setView(view)
            .setPositiveButton("Agregar") { _, _ ->
                val t = etTitulo.text.toString().trim()
                val d = etDesc.text.toString().trim()
                if (t.isNotEmpty()) onAdd(t, d)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
