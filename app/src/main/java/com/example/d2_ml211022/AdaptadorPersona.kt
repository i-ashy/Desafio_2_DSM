package com.example.d2_ml211022

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Spinner
import com.example.d2_ml211022.datos.Persona

class AdaptadorPersona(
    private val context: Activity,
    private val personas: List<Persona>
) : ArrayAdapter<Persona>(context, R.layout.persona_layout, personas) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val view = convertView ?: context.layoutInflater.inflate(R.layout.persona_layout, null)

        val tvCarnet = view.findViewById<TextView>(R.id.tvCarnet)
        val tvNombre = view.findViewById<TextView>(R.id.tvNombre)
        val tvAsignatura = view.findViewById<TextView>(R.id.tvAsignatura)
        val tvEstado = view.findViewById<TextView>(R.id.tvEstado)
        val tvCiclo = view.findViewById<TextView>(R.id.tvCiclo)
        val tvAnio = view.findViewById<TextView>(R.id.tvAnio)
        val tvNotaF = view.findViewById<TextView>(R.id.tvNotaF)
        val tvMatricula = view.findViewById<TextView>(R.id.tvMatricula)

        tvCarnet.text = "Carnet: ${personas[position].carnet}"
        tvNombre.text = "Nombre: ${personas[position].nombre}"
        tvAsignatura.text = "Asignatura: ${personas[position].asignatura}"
        tvEstado.text = "Estado: ${personas[position].estado}"
        tvCiclo.text = "Ciclo: ${personas[position].ciclo}"
        tvAnio.text = "Año: ${personas[position].anio}"
        tvNotaF.text = "Nota Final: ${personas[position].nota}"
        tvMatricula.text = "Matricula: ${personas[position].matricula}"
        return view
    }
}