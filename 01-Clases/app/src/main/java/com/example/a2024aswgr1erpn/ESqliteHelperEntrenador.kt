package com.example.a2024aswgr1erpn

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ESqliteHelperEntrenador(
    contexto:Context
): SQLiteOpenHelper(
    contexto,
    "moviles",
    null,
    1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        val scriptSQLCrearTablaEntrenador =
            """
                CREATE TABLE ENTRENADOR(
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre VARCHAR(50),
                    descripcion VARCHAR(50)
                )
            """.trimIndent()
        db?.execSQL(scriptSQLCrearTablaEntrenador)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}

    fun crearEntrenador(
            nombre: String,
            descripcion: String
    ):Boolean{
        val baseDatosEscritura = writableDatabase
        val valoresAGuardar = ContentValues()
        valoresAGuardar.put("nombre", nombre)
        valoresAGuardar.put("descripcion", descripcion)
        val resultadoGuardar = baseDatosEscritura.insert(
                                    "ENTRENADOR", //nombre tabla
                            null,
                                        valoresAGuardar
                                )
        baseDatosEscritura.close()
        return  if(resultadoGuardar.toInt() == -1) false else true
    }

    fun eliminarEntrenadorFormulario(id:Int):Boolean{
        val conexionEscritura = writableDatabase
        //Consulta SQL: where ... ID=? AND NOMBRE=? [?=1,?=2]
        val parametrosConsultaDelete = arrayOf(id.toString())
        val resultadoEliminacion = conexionEscritura.delete(
                                  "ENTRENADOR",
                             "id=?",
                                        parametrosConsultaDelete
                                    )
        conexionEscritura.close()
        return if(resultadoEliminacion.toInt() == -1) false else true
    }

    fun actualizarEntrenadorFormulario(nombre: String, descripcion: String, id: Int):Boolean{
        val conexionEscritura = writableDatabase
        val valoresAActualizar = ContentValues()
        valoresAActualizar.put("nombre", nombre)
        valoresAActualizar.put("descripcion", descripcion)
        //where: ...
        val parametrosConsultaActualizar = arrayOf(id.toString())
        val resultadoActualizacion = conexionEscritura.update("ENTRENADOR",
            valoresAActualizar, // nombre: Edwin descripcion: B
            "id=?",  //id=1
            parametrosConsultaActualizar)//[1]
        conexionEscritura.close()
        return if(resultadoActualizacion.toInt() == -1) false else true
    }

    fun consultarPorID(id: Int):BEntrenador?{
        val baseDeDatosLectura = readableDatabase
        val scriptConsultaLectura = """
                SELECT * FROM ENTRENADOR WHERE ID = ?
            """.trimIndent()
        val arregloParametrosConsultaLecutra = arrayOf(id.toString())
        val resultadoConsultaLectura = baseDeDatosLectura.rawQuery(scriptConsultaLectura,arregloParametrosConsultaLecutra)
        //Logica Busqueda
        //Recibimos un arreglo (puede ser nulo)
        //Llenar el arreglo de entrenadores
        //Si esta vacio el arreglo no tiene elementos
        val existeAlmenosUno = resultadoConsultaLectura.moveToFirst()
        val arregloRespuesta = arrayListOf<BEntrenador>()
        if(existeAlmenosUno){
            do {
                val entrenador = BEntrenador(resultadoConsultaLectura.getInt(0),resultadoConsultaLectura.getString(1), resultadoConsultaLectura.getString(2))
                arregloRespuesta.add(entrenador)
            }while (resultadoConsultaLectura.moveToNext())
        }
        resultadoConsultaLectura.close()
        baseDeDatosLectura.close()
        return if (arregloRespuesta.size > 0) arregloRespuesta[0] else null
    }
}