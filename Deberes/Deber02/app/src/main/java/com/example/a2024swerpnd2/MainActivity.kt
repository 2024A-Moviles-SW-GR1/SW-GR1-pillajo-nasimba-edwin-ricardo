package com.example.a2024swerpnd2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private var listaClientes:ArrayList<Cliente> = arrayListOf()
    private var adapter:ArrayAdapter<Cliente>? = null
    private var posicionitemSeleccionadoMenu: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BaseDeDatos.dbHelper = SQLiteHelper(this)

        val listaClientesMain = findViewById<ListView>(R.id.id_lista_cliente)
        listaClientes = BaseDeDatos.dbHelper!!.obtenerTodosLosClientes()


        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaClientes)
        listaClientesMain.adapter = adapter
        adapter!!.notifyDataSetChanged()
        val botonCrear = findViewById<Button>(R.id.btn_crear_cliente)
        botonCrear.setOnClickListener {
            irActividad(CrearClienteActivity::class.java)
        }
        registerForContextMenu(listaClientesMain)

    }
    fun irActividad(clase: Class<*>, clienteSeleccionado: Cliente? = null) {
        val intent = Intent(this, clase)
        if(clienteSeleccionado != null){
            intent.putExtra("cliente", clienteSeleccionado)
        }
        startActivity(intent)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //llenar las opciones del menu
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_cliente, menu)
        //Obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionitemSeleccionadoMenu = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editar_cliente_menu -> {
                val clienteSeleccionado: Cliente? = obtenerClienteSeleccionado(posicionitemSeleccionadoMenu)
                irActividad(CrearClienteActivity::class.java, clienteSeleccionado)
                return true
            }

            R.id.eliminar_cliente_menu -> {
                abrirDialogo()//
                return true
            }
            R.id.pedidos_cliente_menu -> {
                val clienteSeleccionado: Cliente? = obtenerClienteSeleccionado(posicionitemSeleccionadoMenu)
                irActividad(ListaPedido::class.java, clienteSeleccionado)
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun obtenerClienteSeleccionado(posicionClienteSeleccionadoMenu: Int): Cliente? {
        return if(posicionClienteSeleccionadoMenu > -1) listaClientes[posicionClienteSeleccionadoMenu] else null
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea Eliminar al cliente?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                val clienteSeleccionado: Cliente? = obtenerClienteSeleccionado(posicionitemSeleccionadoMenu)
                val respuesta = BaseDeDatos.dbHelper!!.eliminarCliente(
                    clienteSeleccionado?.idCliente
                )
                if (respuesta){
                    listaClientes = BaseDeDatos.dbHelper!!.obtenerTodosLosClientes()
                    // Actualizar el adaptador con la nueva lista de clientes
                    adapter?.clear() // Limpiar los datos antiguos del adaptador
                    adapter?.addAll(listaClientes) // Agregar los nuevos datos
                    adapter?.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
                    mostrarSnackbar("Cliente eliminado!")
                }
            }
        )

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()

    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_main_activity),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}