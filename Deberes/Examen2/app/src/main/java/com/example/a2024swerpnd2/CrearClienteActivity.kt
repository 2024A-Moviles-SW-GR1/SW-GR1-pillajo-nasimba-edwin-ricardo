package com.example.a2024swerpnd2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class CrearClienteActivity : AppCompatActivity() {
    private var cliente: Cliente? = null
    private var esNuevoCliente: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_cliente)

        // Obtener cliente del intent
        cliente = intent.getParcelableExtra("cliente")
        esNuevoCliente = cliente == null

        configurarFormulario()

        val nombre = findViewById<EditText>(R.id.id_nombre_cliente)
        val email = findViewById<EditText>(R.id.id_email_cliente)
        val telefono = findViewById<EditText>(R.id.id_telefono_cliente)
        val estadoCivil = findViewById<EditText>(R.id.id_estadoc_cliente)
        val edad = findViewById<EditText>(R.id.id_edad_cliente)
        val botonCrear = findViewById<Button>(R.id.btn_crear_cliente)


        botonCrear.setOnClickListener {
            if (esNuevoCliente){
                val respuesta = BaseDeDatos.dbHelper!!.crearCliente(nombre.text.toString(),
                    email.text.toString(),
                    telefono.text.toString(),
                    estadoCivil.text.toString().first(),
                    edad.text.toString().toInt())

                if (respuesta){
                    irActividad(MainActivity::class.java)
                }else{mostrarSnackbar("Cliente no creado!")}
            }else{
                val respuesta = BaseDeDatos.dbHelper!!.actualizarCliente(nombre.text.toString(),
                    email.text.toString(),
                    telefono.text.toString(),
                    estadoCivil.text.toString()[0],
                    edad.text.toString().toInt(),
                    cliente?.idCliente)

                if (respuesta){
                    irActividad(MainActivity::class.java)
                }else{mostrarSnackbar("Cliente no actualizado!")}
            }
        }

    }

    private fun configurarFormulario() {
        val nombre = findViewById<EditText>(R.id.id_nombre_cliente)
        val email = findViewById<EditText>(R.id.id_email_cliente)
        val telefono = findViewById<EditText>(R.id.id_telefono_cliente)
        val estadoCivil = findViewById<EditText>(R.id.id_estadoc_cliente)
        val edad = findViewById<EditText>(R.id.id_edad_cliente)
        val botonCrear = findViewById<Button>(R.id.btn_crear_cliente)
        val titulo = findViewById<TextView>(R.id.titulo_crear_cliente)

        if (esNuevoCliente) {
            botonCrear.setText("Crear")
            titulo.setText("Crear Cliente")
        } else {
            botonCrear.setText("Actualizar")
            titulo.setText("Actualizar Cliente")

            // Configura el formulario para editar un cliente existente
            nombre.setText(cliente?.nombre)
            email.setText(cliente?.email)
            telefono.setText(cliente?.telefono)
            estadoCivil.setText(cliente?.estadoCivil.toString()+" ")
            edad.setText(cliente?.edad.toString())
        }
    }

    private fun irActividad(clase: Class<*>) {
            val intent = Intent(this, clase)
            startActivity(intent)
    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_crear_cliente),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}