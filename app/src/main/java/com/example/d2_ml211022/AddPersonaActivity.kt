package com.example.d2_ml211022

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.example.d2_ml211022.datos.Persona

class AddPersonaActivity : AppCompatActivity() {

    // Declaración de variables
    private lateinit var txtNombre: EditText
    private lateinit var txtCarnet: EditText
    private lateinit var cbAsignatura: Spinner
    private lateinit var cbEstado: Spinner
    private lateinit var cbCiclo: Spinner
    private lateinit var cbAnio: Spinner
    private lateinit var txtNota: EditText
    private lateinit var cbMatricula: Spinner
    private lateinit var database: DatabaseReference
    private var key = ""
    private var accion = ""

    // se llama al iniciar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_persona)
        inicializar()
    }

    private fun inicializar() {

        // Inicializar los componentes de la interfaz
        txtNombre = findViewById(R.id.txtNombre)
        txtCarnet = findViewById(R.id.txtCarnet)
        cbAsignatura = findViewById(R.id.cbAsignatura)
        cbEstado = findViewById(R.id.cbEstadoAsignatura)
        cbCiclo = findViewById(R.id.cbCiclo)
        cbAnio = findViewById(R.id.cbAnio)
        txtNota = findViewById(R.id.txtNota)
        cbMatricula = findViewById(R.id.cbMatricula)

        // Inicializar los spinners con los datos de los arrays
        // Asignatura
        ArrayAdapter.createFromResource(
            this,
            R.array.array_asignatura,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cbAsignatura.adapter = adapter
        }

        //estado
        ArrayAdapter.createFromResource(
            this,
            R.array.array_estado,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cbEstado.adapter = adapter
        }

        //ciclo
        ArrayAdapter.createFromResource(
            this,
            R.array.array_ciclo,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cbCiclo.adapter = adapter
        }

        //anio
        ArrayAdapter.createFromResource(
            this,
            R.array.array_anio,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cbAnio.adapter = adapter
        }

        //matricula
        ArrayAdapter.createFromResource(
            this,
            R.array.array_matricula,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cbMatricula.adapter = adapter
        }

        // Obtener los datos del Intent
        key = intent.getStringExtra("key") ?: "" // Clave para editar
        accion = intent.getStringExtra("accion") ?: "" // Acción (agregar o editar)

        txtNombre.setText(intent.getStringExtra("nombre"))
        txtCarnet.setText(intent.getStringExtra("carnet"))
        setSeleccionarSpinner(cbAsignatura, intent.getStringExtra("asignatura") ?: "")
        setSeleccionarSpinner(cbEstado, intent.getStringExtra("estado") ?: "")
        setSeleccionarSpinner(cbCiclo, intent.getStringExtra("ciclo") ?: "")
        setSeleccionarSpinner(cbAnio, intent.getStringExtra("anio") ?: "")
        setSeleccionarSpinner(cbMatricula, intent.getStringExtra("matricula") ?: "")
        txtNota.setText(intent.getStringExtra("nota"))
        database = FirebaseDatabase.getInstance().getReference("personas")
    }

    // funcion para seleccionar el valor que sea igual al que se le pase
    private fun setSeleccionarSpinner(spinner: Spinner, valor: String) {
        val adapter = spinner.adapter
        for (i in 0 until adapter.count) {
            if (adapter.getItem(i).toString() == valor) {
                spinner.setSelection(i)
                break
            }
        }
    }

    fun guardar(view: View) {

        val nombre = txtNombre.text.toString()
        val carnet = txtCarnet.text.toString()
        val asignatura = cbAsignatura.selectedItem.toString()
        val estado = cbEstado.selectedItem.toString()
        val ciclo = cbCiclo.selectedItem.toString()
        val anio = cbAnio.selectedItem.toString()
        val nota = txtNota.text.toString()
        val matricula = cbMatricula.selectedItem.toString()
        val persona = Persona(carnet, nombre, asignatura, estado, ciclo, anio, nota, matricula)

        when (accion) {
            "a" -> { // Agregar
                database.push().setValue(persona).addOnSuccessListener {
                    Toast.makeText(this, "Registro guardado", Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, "Error al guardar", Toast.LENGTH_SHORT).show()
                }
            }
            "e" -> { // Editar
                if (key.isNotEmpty()) {
                    database.child(key).setValue(persona).addOnSuccessListener {
                        Toast.makeText(this, "Registro actualizado", Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        finish()
    }

    fun cancelar(view: View) {
        finish()
    }
}