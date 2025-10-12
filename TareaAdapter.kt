package com.example.juegoproyecto.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.juegoproyecto.R
import com.example.juegoproyecto.model.Tarea

class TareaAdapter(private var lista: List<Tarea>) :
    RecyclerView.Adapter<TareaAdapter.TareaViewHolder>() {

    inner class TareaViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val tvTitulo: TextView = v.findViewById(R.id.tvTitulo)
        val tvDescripcion: TextView = v.findViewById(R.id.tvDescripcion)
        val tvFecha: TextView = v.findViewById(R.id.tvFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TareaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarea, parent, false)
        return TareaViewHolder(view)
    }

    override fun onBindViewHolder(holder: TareaViewHolder, position: Int) {
        val tarea = lista[position]
        holder.tvTitulo.text = tarea.titulo
        holder.tvDescripcion.text = tarea.descripcion
        holder.tvFecha.text = tarea.fecha
    }

    override fun getItemCount(): Int = lista.size

    fun actualizar(nuevaLista: List<Tarea>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }
}
