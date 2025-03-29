package com.example.d2_ml211022

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.app.AlertDialog
import android.widget.AdapterView
import android.widget.ListView
import com.example.d2_ml211022.datos.Persona
import com.google.firebase.database.*
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var listaPersonas: ListView
    private var personas: MutableList<Persona> = mutableListOf()
    private lateinit var consultaOrdenada: Query

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializar()

        val btnLogOut = findViewById<Button>(R.id.btnLogout)

        btnLogOut.setOnClickListener {
            logOut()
        }
    }

    private fun inicializar() {

        val fabAgregar: FloatingActionButton = findViewById(R.id.fabAgregar)
        listaPersonas = findViewById(R.id.Lista)

        // Configurar listeners para la lista
        listaPersonas.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, AddPersonaActivity::class.java)
            intent.putExtra("accion", "e") // Editar
            intent.putExtra("key", personas[position].key)
            intent.putExtra("nombre", personas[position].nombre)
            intent.putExtra("carnet", personas[position].carnet)
            intent.putExtra("asignatura", personas[position].asignatura)
            intent.putExtra("estado", personas[position].estado)
            intent.putExtra("ciclo", personas[position].ciclo)
            intent.putExtra("anio", personas[position].anio)
            intent.putExtra("nota", personas[position].nota)
            intent.putExtra("matricula", personas[position].matricula)
            startActivity(intent)
        }

        listaPersonas.onItemLongClickListener = AdapterView.OnItemLongClickListener { _, _, position, _ ->
            AlertDialog.Builder(this)
                .setTitle("Confirmación")
                .setMessage("¿Está seguro de eliminar registro?")
                .setPositiveButton("Sí") { _, _ ->
                    personas[position].key?.let { key ->
                        refPersonas.child(key).removeValue()
                        Toast.makeText(this, "Registro borrado", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("No") { _, _ ->
                    Toast.makeText(this, "Acción cancelada", Toast.LENGTH_SHORT).show()
                }
                .show()
            true
        }

        fabAgregar.setOnClickListener {
            val intent = Intent(this, AddPersonaActivity::class.java)
            intent.putExtra("accion", "a") // Agregar
            intent.putExtra("key", "")
            intent.putExtra("nombre", "")
            intent.putExtra("carnet", "")
            intent.putExtra("asignatura", "")
            intent.putExtra("estado", "")
            intent.putExtra("ciclo", "")
            intent.putExtra("anio", "")
            intent.putExtra("nota", "")
            intent.putExtra("matricula", "")
            startActivity(intent)
        }

        // Configurar consulta a Firebase
        consultaOrdenada = refPersonas.orderByChild("nombre")
        consultaOrdenada.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                personas.clear()
                for (data in snapshot.children) {
                    val persona = data.getValue(Persona::class.java)
                    persona?.key = data.key
                    persona?.let { personas.add(it) }
                }
                listaPersonas.adapter = AdaptadorPersona(this@MainActivity, personas)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    companion object {
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val refPersonas: DatabaseReference = database.getReference("personas")
    }

    private fun logOut() {
        FirebaseAuth.getInstance()
            .signOut()
            .also {
                Toast.makeText(
                    this,
                    "Sesión cerrada",
                    Toast.LENGTH_SHORT
                ).show()

                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
}