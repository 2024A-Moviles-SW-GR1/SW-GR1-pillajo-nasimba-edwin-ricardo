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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar

class ListaPedido : AppCompatActivity() {

    private var cliente: Cliente? = null
    private var listaPedidos:ArrayList<Pedido> = arrayListOf()
    private var adapter: ArrayAdapter<Pedido>? = null
    private var posicionitemSeleccionadoMenu: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_pedido)

        // Obtener cliente del intent
        cliente = intent.getParcelableExtra("cliente")
        BaseDeDatos.dbHelper = SQLiteHelper(this)
        findViewById<TextView>(R.id.id_texto_pedido_cliente).setText("Pedido del cliente ${cliente?.nombre}")

        val listaPedidosCliente = findViewById<ListView>(R.id.id_lista_pedido_cliente)
        listaPedidos = BaseDeDatos.dbHelper!!.obtenerTodosLosPedidosCliente(cliente!!.idCliente)

        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1,listaPedidos)
        listaPedidosCliente.adapter = adapter
        adapter!!.notifyDataSetChanged()
        val botonCrear = findViewById<Button>(R.id.btn_crear_pedido_cliente)
        botonCrear.setOnClickListener {
            irActividad(CrearPedidoActivity::class.java, cliente)
        }
        registerForContextMenu(listaPedidosCliente)
    }
    fun irActividad(clase: Class<*>, cliente:Cliente? = null, pedido:Pedido? = null) {
        val intent = Intent(this, clase)
        if(cliente != null){
            intent.putExtra("cliente", cliente)
        }
        if(pedido != null){
            intent.putExtra("pedido", pedido)
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
        inflater.inflate(R.menu.menu_lista_pedidos, menu)
        //Obtener id
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val posicion = info.position
        posicionitemSeleccionadoMenu = posicion
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.editar_pedido_menu -> {
                val pedidoSeleccionado: Pedido? = obtenerPedidoSeleccionado(posicionitemSeleccionadoMenu)
                irActividad(CrearPedidoActivity::class.java, pedido = pedidoSeleccionado)
                return true
            }

            R.id.eliminar_pedido_menu -> {
                abrirDialogo()//
                return true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun obtenerPedidoSeleccionado(posicionPedidoSeleccionadoMenu: Int): Pedido? {
        return if(posicionPedidoSeleccionadoMenu > -1) listaPedidos[posicionPedidoSeleccionadoMenu] else null
    }

    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("¿Desea Eliminar al cliente?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                    dialog, which ->
                val pedidoSeleccionado: Pedido? = obtenerPedidoSeleccionado(posicionitemSeleccionadoMenu)
                val respuesta = BaseDeDatos.dbHelper!!.eliminarPedido(
                    pedidoSeleccionado?.idPedido
                )
                if (respuesta){
                    listaPedidos = BaseDeDatos.dbHelper!!.obtenerTodosLosPedidosCliente(cliente!!.idCliente)
                    // Actualizar el adaptador con la nueva lista de clientes
                    adapter?.clear() // Limpiar los datos antiguos del adaptador
                    adapter?.addAll(listaPedidos) // Agregar los nuevos datos
                    adapter?.notifyDataSetChanged() // Notificar al adaptador que los datos han cambiado
                    mostrarSnackbar("Pedido eliminado!")
                }
            }
        )

        builder.setNegativeButton("Cancelar", null)
        builder.create().show()

    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_lista_pedido),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }
}

