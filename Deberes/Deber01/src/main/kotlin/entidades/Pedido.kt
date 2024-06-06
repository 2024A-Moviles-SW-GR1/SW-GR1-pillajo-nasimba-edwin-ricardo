package entidades

import java.text.SimpleDateFormat
import java.util.*


data class Pedido(
    var id: Int?=null,
    var clienteId: Int?=null,
    val descripcion: String,
    val cantidad: Int,
    var precioUnitario: Double,
    var fechaPedido: Date?=null,
    val estado: Char
){

    fun setId(pedidoID: Int) {
        id = pedidoID
    }

    fun setIDCliente(idCliente: Int) {
        clienteId = idCliente
    }

    fun setPrecio(precio: Double) {
        precioUnitario = precio
    }
    fun setFecha(fecha: Date) {
        fechaPedido = fecha
    }
    override fun toString(): String {
        var idc = ""
        idc = if (clienteId == null){
            "Sin asignar"
        }else{
            clienteId.toString()
        }
        val formato = "dd-MM-yyyy"
        val formateador = SimpleDateFormat(formato)
        val fechaFormateada:String = formateador.format(fechaPedido)
        return "ID=$id, ID Cliente= $idc, Descripcion=$descripcion, Cantidad=$cantidad, Precio Unitario=$precioUnitario, Fecha Pedido=$fechaFormateada, Estado=$estado"
    }
}