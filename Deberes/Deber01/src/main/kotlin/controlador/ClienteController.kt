package controlador

import com.google.gson.Gson
import entidades.Cliente
import entidades.Pedido
import java.io.File
import java.io.IOException


class ClienteController() {
    private val cliente_file = File("src/main/kotlin/archivos/cliente.json");
    private val gson = Gson()

    init {
        if (!cliente_file.exists()) {
            try {
                cliente_file.createNewFile()
            } catch (e: IOException) {
                println("Error al crear el archivo: ${e.message}")
            }
        } else {
            cliente_file
        }
    }

    fun agregarCliente(cliente: Cliente) {
        val clientes = listarClientes().toMutableList()
        cliente.setId(calcularId());
        clientes.add(cliente)
        guardarEnArchivo(clientes)
    }

    private fun calcularId(): Int {
        if (cliente_file.length() == 0L) {
            return 1;
        } else {
            val json = cliente_file.readText()
            val clienteId: Int = gson.fromJson(json, Array<Cliente>::class.java).toList().last().idCliente!!;
            return clienteId + 1;
        }
    }

    fun listarClientes(): List<Cliente> {
        return if (cliente_file.length() == 0L) {
            emptyList()
        } else {
            val json = cliente_file.readText()
            gson.fromJson(json, Array<Cliente>::class.java).toList()
        }
    }

    fun listarClientePorID(id: Int): Cliente? {
        val cliente = listarClientes();
        cliente.forEach {
            if (it.idCliente == id) {
                return it
            }
        }
        return null;
    }

    fun actualizarCliente(cliente: Cliente, telefonoCliente: String) {
        val clientes = listarClientes().toMutableList()
        clientes.filter { it.idCliente == cliente.idCliente }[0].seTelefono(telefonoCliente)
        guardarEnArchivo(clientes)
    }

    fun eliminarCliente(cliente: Cliente) {
        val clientes = listarClientes().toMutableList()
        val clienteE = clientes.find { it.idCliente == cliente.idCliente }
        clienteE?.let {
            clientes.remove(it)
            guardarEnArchivo(clientes)
        }
    }

    private fun guardarEnArchivo(clientes: MutableList<Cliente>) {
        val json = gson.toJson(clientes)
        cliente_file.writeText(json)
    }

    fun listarPedidosClientePorID(idCliente: Int): List<Pedido> {
        val pedido = PedidoController()
        val pedidos = pedido.listarPedidos()
        val pedidosCliente = pedidos.filter { it.clienteId == idCliente }
        return if (pedidosCliente.isEmpty()) {
            emptyList()
        } else {
            pedidosCliente
        }
    }

    fun verificarPedidos(idCliente: Int): Boolean {
        val pedido = PedidoController()
        return pedido.listarPedidos().any { it.clienteId == idCliente }
    }
}
