package com.example.d2_ml211022.datos

import java.util.*

class Persona {
    var carnet: String? = null
    var nombre: String? = null
    var asignatura: String? = null
    var estado: String? = null
    var ciclo: String? = null
    var anio: String? = null
    var nota: String? = null
    var matricula: String? = null
    var key: String? = null

    constructor() {}
    constructor(
        carnet: String?,
        nombre: String?,
        asignatura: String?,
        estado: String?,
        ciclo: String?,
        anio: String?,
        nota: String?,
        matricula: String?) {

        this.carnet = carnet
        this.nombre = nombre
        this.asignatura = asignatura
        this.estado = estado
        this.ciclo = ciclo
        this.anio = anio
        this.nota = nota
        this.matricula = matricula
    }

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "carnet" to carnet,
            "nombre" to nombre,
            "asignatura" to asignatura,
            "estado" to estado,
            "ciclo" to ciclo,
            "anio" to anio,
            "nota" to nota,
            "matricula" to matricula,
            "key" to key
        )
    }
}