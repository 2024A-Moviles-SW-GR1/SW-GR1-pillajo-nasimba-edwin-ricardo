package com.example.a2024aswgr1erpn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar

class ECrudEntrenador : AppCompatActivity() {
    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(
            findViewById(R.id.cl_sqlite),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ecrud_entrenador)

        //buscar entrenador
        val botonBuscarBD = findViewById<Button>(R.id.btn_buscar_bdd)
        botonBuscarBD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val entrenador = EBaseDeDatos.tablaEntrenador!!.consultarPorID(
                id.toString().toInt()
            )
            if (entrenador == null) {
                mostrarSnackbar("Entrenador no encontrado")
                id.setText("")
                nombre.setText("")
                descripcion.setText("")
            } else {
                id.setText(entrenador.id.toString())
                nombre.setText(entrenador.nombre)
                descripcion.setText(entrenador.descripcion)
                mostrarSnackbar("Entrenador encontrado")
            }
        }

        val botonCrearBD = findViewById<Button>(R.id.btn_crear_bdd)
        botonCrearBD.setOnClickListener {
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.crearEntrenador(
                nombre.text.toString(),
                descripcion.text.toString()
            )
            if (respuesta) mostrarSnackbar("Entrenador Creado!")
        }

        val botonActualizarBD = findViewById<Button>(R.id.btn_actualizar_bdd)
        botonActualizarBD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val nombre = findViewById<EditText>(R.id.input_nombre)
            val descripcion = findViewById<EditText>(R.id.input_descripcion)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.actualizarEntrenadorFormulario(
                nombre.text.toString(),
                descripcion.text.toString(),
                id.text.toString().toInt()
            )
            if (respuesta) mostrarSnackbar("Entrenador Actualizado!")
        }

        val botonEliminarBD = findViewById<Button>(R.id.btn_eliminar_bdd)
        botonEliminarBD.setOnClickListener {
            val id = findViewById<EditText>(R.id.input_id)
            val respuesta = EBaseDeDatos.tablaEntrenador!!.eliminarEntrenadorFormulario(
                id.text.toString().toInt()
            )
            if (respuesta) mostrarSnackbar("Entrenador Eliminado!")
        }
    }
}