package com.example.juegoproyecto.repository

import android.content.Context
import com.example.juegoproyecto.database.TareaDao
import com.example.juegoproyecto.model.Tarea
import com.example.juegoproyecto.network.RetrofitClient
import com.example.juegoproyecto.util.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class TareaRepository(
    private val dao: TareaDao,
    private val context: Context
) {

    companion object {
        //ID Google Sheet
        const val SHEET_ID = "15ZgwHx4HVyMhjdYSk48Z0yqWgoOf_g_1KwGlJf6infw"
        //URL para obtener el JSON
        const val SHEET_URL = "https://docs.google.com/spreadsheets/d/$SHEET_ID/gviz/tq?tqx=out:json"
    }
    suspend fun obtenerTareas(): List<Tarea> = withContext(Dispatchers.IO) {
        try {
            if (NetworkUtils.isOnline(context)) {
                val response = RetrofitClient.api.obtenerSheetRaw(SHEET_URL)

                if (response.isSuccessful && response.body() != null) {
                    val lista = parsearJSON(response.body()!!)
                    dao.eliminarTodas()
                    dao.insertarTodas(lista)
                    return@withContext lista
                }
            }
            dao.obtenerTareas()
        } catch (e: Exception) {
            e.printStackTrace()
            dao.obtenerTareas()
        }
    }

    private fun parsearJSON(raw: String): List<Tarea> {
        val lista = mutableListOf<Tarea>()
        try {
            val start = raw.indexOf("{")
            val end = raw.lastIndexOf("}")
            val jsonStr = raw.substring(start, end + 1)
            val json = JSONObject(jsonStr)
            val rows = json.getJSONObject("table").getJSONArray("rows")

            for (i in 0 until rows.length()) {
                val c = rows.getJSONObject(i).getJSONArray("c")
                val id = c.getJSONObject(0).opt("v").toString().toDouble().toInt()
                val titulo = c.getJSONObject(1).optString("v")
                val desc = c.getJSONObject(2).optString("v")
                val fecha = c.getJSONObject(3).optString("v")
                val completed = c.getJSONObject(4).optBoolean("v", false)

                lista.add(Tarea(id, titulo, desc, fecha, completed))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return lista
    }
    suspend fun agregarTarea(t: Tarea) = withContext(Dispatchers.IO) {
        dao.insertar(t)
    }
}

