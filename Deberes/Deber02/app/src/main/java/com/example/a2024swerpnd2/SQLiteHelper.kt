package com.example.a2024swerpnd2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SQLiteHelper(contexto: Context):
    SQLiteOpenHelper(contexto,
        "Appmoviles",
        null,
        1) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaCliente =
            """
                CREATE TABLE CLIENTE(
                    idCliente INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    email VARCHAR(50),
                    telefono VARCHAR(10),
                    estadoCivil CHAR(1),
                    edad INTEGER
                )
            """.trimIndent()
        val scriptSQLCrearTablaPedido =
            """
                CREATE TABLE PEDIDO(
                    idPedido INTEGER PRIMARY KEY AUTOINCREMENT,
                    descripcion VARCHAR(100),
                    cantidad INTEGER,
                    precioUnitario DOUBLE,
                    estado CHAR(1),
                    clienteid INTEGER,
                    FOREIGN KEY (clienteid) REFERENCES CLIENTE(idCliente)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaCliente)
        db?.execSQL(scriptSQLCrearTablaPedido)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun obtenerTodosLosClientes(): ArrayList<Cliente>{
        val baseDeDatosLectura = readableDatabase
        val scriptConsultaLectura = """
                SELECT * FROM CLIENTE
            """.trimIndent()

        val resultadoConsultaLectura = baseDeDatosLectura.rawQuery(scriptConsultaLectura,
            emptyArray()
        )
        val arregloRespuesta = arrayListOf<Cliente>()
        if(resultadoConsultaLectura.moveToFirst()){
            do {
                val cliente = Cliente(resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getString(2),
                    resultadoConsultaLectura.getString(3),
                    resultadoConsultaLectura.getString(4)[0],
                    resultadoConsultaLectura.getInt(5)

                )
                arregloRespuesta.add(cliente)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDeDatosLectura.close()
        return arregloRespuesta
    }

    fun crearCliente(
        nombre: String,
        email: String,
        telefono: String,
        estadoCivil: Char,
        edad: Int
    ):Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("email", email)
        valoresAGuardar.put("telefono", telefono)
        valoresAGuardar.put("estadoCivil", estadoCivil.toString())
        valoresAGuardar.put("edad", edad)
        val resultadoGuardar = baseDatosEscritura.insert(
            "CLIENTE", //nombre tabla
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun actualizarCliente(
        nombre: String,
        email: String,
        telefono: String,
        estadoCivil: Char,
        edad: Int,
        idCliente: Int?
    ): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("email", email)
        valoresAActualizar.put("telefono", telefono)
        valoresAActualizar.put("estadoCivil", estadoCivil.toString())
        valoresAActualizar.put("edad", edad)
        //where: ...
        val parametrosConsultaActualizar = arrayOf(idCliente.toString())
        val resultadoActualizacion = conexionEscritura.update("CLIENTE",
            valoresAActualizar, // nombre: Edwin descripcion: B
            "idCliente=?",  //id=1
            parametrosConsultaActualizar)//[1]
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }

    fun eliminarCliente(idCliente: Int?): Boolean {
        val conexionEscritura = writableDatabase
        //Consulta SQL: where ... ID=? AND NOMBRE=? [?=1,?=2]
        val parametrosConsultaDelete = arrayOf(idCliente.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "CLIENTE",
            "idCliente=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun obtenerTodosLosPedidosCliente(idCliente: Int): ArrayList<Pedido> {
        val baseDeDatosLectura = readableDatabase
        val scriptConsultaLectura = """
                SELECT * FROM PEDIDO WHERE clienteid = ?
            """.trimIndent()
        val arregloParametrosConsultaLectura = arrayOf(idCliente.toString())
        val resultadoConsultaLectura = baseDeDatosLectura.rawQuery(scriptConsultaLectura,
            arregloParametrosConsultaLectura)
        val existeAlmenosUno = resultadoConsultaLectura.moveToFirst()
        val arregloRespuesta = arrayListOf<Pedido>()
        if(existeAlmenosUno){
            do {
                val pedido = Pedido(resultadoConsultaLectura.getInt(0),
                    resultadoConsultaLectura.getString(1),
                    resultadoConsultaLectura.getInt(2),
                    resultadoConsultaLectura.getDouble(3),
                    resultadoConsultaLectura.getString(4)[0],
                    resultadoConsultaLectura.getInt(5))
                arregloRespuesta.add(pedido)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDeDatosLectura.close()
        return arregloRespuesta
    }

    fun eliminarPedido(idPedido: Int?): Boolean {
        val conexionEscritura = writableDatabase
        //Consulta SQL: where ... ID=? AND NOMBRE=? [?=1,?=2]
        val parametrosConsultaDelete = arrayOf(idPedido.toString())
        val resultadoEliminacion = conexionEscritura.delete(
            "PEDIDO",
            "idPedido=?",
            parametrosConsultaDelete
        )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun crearPedido(descripcion: String,
                    cantidad: Int,
                    precioUnitario: Double,
                    estado: Char,
                    clienteid: Int): Boolean {
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("descripcion", descripcion)
        valoresAGuardar.put("cantidad", cantidad)
        valoresAGuardar.put("precioUnitario", precioUnitario)
        valoresAGuardar.put("estado", estado.toString())
        valoresAGuardar.put("clienteid", clienteid)
        val resultadoGuardar = baseDatosEscritura.insert(
            "PEDIDO", //nombre tabla
            null,
            valoresAGuardar
        )
        baseDatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun actualizarPedido(descripcion: String,
                         cantidad: Int,
                         precioUnitario: Double,
                         estado: Char,
                         idPedido: Int?): Boolean {
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("descripcion", descripcion)
        valoresAActualizar.put("cantidad", cantidad)
        valoresAActualizar.put("precioUnitario", precioUnitario)
        valoresAActualizar.put("estado", estado.toString())
        //where: ...
        val parametrosConsultaActualizar = arrayOf(idPedido.toString())
        val resultadoActualizacion = conexionEscritura.update("Pedido",
            valoresAActualizar, // nombre: Edwin descripcion: B
            "idPedido=?",  //id=1
            parametrosConsultaActualizar)//[1]
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }
}