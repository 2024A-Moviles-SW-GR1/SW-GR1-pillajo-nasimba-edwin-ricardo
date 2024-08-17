package com.example.a2024swerpnd2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class CrearPedidoActivity : AppCompatActivity() {
    private var pedido: Pedido? = null
    private var cliente: Cliente? = null
    private var esNuevoPedido: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_pedido)

        // Obtener cliente y pedido del intent
        pedido = intent.getParcelableExtra("pedido")
        cliente =intent.getParcelableExtra("cliente")

        esNuevoPedido = pedido == null

        configurarFormulario()

        val descripcion = findViewById<EditText>(R.id.id_descripcion_pedido)
        val cantidad = findViewById<EditText>(R.id.id_cantidad_pedido)
        val precioUnitario = findViewById<EditText>(R.id.id_precio_pedido)
        val estado = findViewById<EditText>(R.id.id_estado_pedido)
        val botonGuardar = findViewById<Button>(R.id.btn_crear_pedido_ac)


        botonGuardar.setOnClickListener {
            if (esNuevoPedido){
                val respuesta = BaseDeDatos.dbHelper!!.crearPedido(descripcion.text.toString(),
                    cantidad.text.toString().toInt(),
                    precioUnitario.text.toString().toDouble(),
                    estado.text.toString()[0],
                    cliente!!.idCliente)
                if (respuesta){
                    irActividad(ListaPedido::class.java)
                }else{mostrarSnackbar("Pedido no creado!")}
            }else{
                val respuesta = BaseDeDatos.dbHelper!!.actualizarPedido(descripcion.text.toString(),
                    cantidad.text.toString().toInt(),
                    precioUnitario.text.toString().toDouble(),
                    estado.text.toString()[0],
                    pedido?.idPedido)

                if (respuesta){
                    irActividad(ListaPedido::class.java)
                }else{mostrarSnackbar("Cliente no actualizado!")}
            }
        }

    }

    private fun configurarFormulario() {
        val descripcion = findViewById<EditText>(R.id.id_descripcion_pedido)
        val cantidad = findViewById<EditText>(R.id.id_cantidad_pedido)
        val precioUnitario = findViewById<EditText>(R.id.id_precio_pedido)
        val estado = findViewById<EditText>(R.id.id_estado_pedido)
        val botonGuardar = findViewById<Button>(R.id.btn_crear_pedido_ac)
        val titulo = findViewById<TextView>(R.id.titulo_crear_pedido_cliente)

        if (esNuevoPedido) {
            botonGuardar.setText("Crear")
            titulo.setText("Crear Pedido")
        } else {
            botonGuardar.setText("Actualizar")
            titulo.setText("Actualizar Pedido")
            // Configura el formulario para editar un pedido existente
            descripcion.setText(pedido!!.descripcion)
            cantidad.setText(pedido!!.cantidad.toString())
            precioUnitario.setText(pedido!!.precioUnitario.toString())
            estado.setText(pedido!!.estado.toString()+" ")
        }
    }

    private fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_crear_pedido),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}