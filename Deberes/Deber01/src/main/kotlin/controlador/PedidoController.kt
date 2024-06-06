package controlador

import com.google.gson.Gson
import entidades.Pedido
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class PedidoController(){

    private val pedido_file = File("src/main/kotlin/archivos/pedido.json");
    private val gson = Gson()

    init {
        if (!pedido_file.exists()) {
            try {
                pedido_file.createNewFile()
            } catch (e: IOException) {
                println("Error al crear el archivo: ${e.message}")
            }
        } else {
            pedido_file
        }
    }

    fun agregarPedido(pedido: Pedido, fecha: String) {
        val formato = "dd-MM-yyyy"
        val formateador = SimpleDateFormat(formato)
        val fechaFormateada: Date = formateador.parse(fecha)
        val pedidos = listarPedidos().toMutableList()
        pedido.setId(calcularId());
        pedido.setFecha(fechaFormateada)
        pedidos.add(pedido)
        guardarEnArchivo(pedidos)
    }

    private fun calcularId(): Int {
        if (pedido_file.length() == 0L){
            return 1;
        }else{
            val json = pedido_file.readText()
            val pedidoId:Int = gson.fromJson(json, Array<Pedido>::class.java).toList().last().id!!;
            return pedidoId + 1;
        }
    }

    fun listarPedidos(): List<Pedido> {
        return if (pedido_file.length() == 0L){
            emptyList()
        }else{
            val json = pedido_file.readText()
            gson.fromJson(json, Array<Pedido>::class.java).toList()
        }
    }

    fun listarPedidoPorID(idPedido: Int): Pedido? {
        val pedido = listarPedidos();
        pedido.forEach {
            if (it.id == idPedido) {
                return it
            }
        }
        return null;
    }

    fun actualizarPedido(producto: Pedido, idCliente: Int) {
        val pedidos = listarPedidos().toMutableList()
        pedidos.filter { it.id == producto.id }[0].setIDCliente(idCliente)
        guardarEnArchivo(pedidos)
    }

    fun actualizarPrecioPedido(producto: Pedido, precio: Double) {
        val pedidos = listarPedidos().toMutableList()
        pedidos.filter { it.id == producto.id }[0].setPrecio(precio)
        guardarEnArchivo(pedidos)
    }

    fun eliminarPedido(pedido: Pedido) {
        val pedidos = listarPedidos().toMutableList()
        val pedidoE = pedidos.find { it.id == pedido.id}
        pedidoE?.let {
            pedidos.remove(it)
            guardarEnArchivo(pedidos)
        }
    }

    private fun guardarEnArchivo(pedidos: MutableList<Pedido>) {
        val json = gson.toJson(pedidos)
        pedido_file.writeText(json)
    }
}