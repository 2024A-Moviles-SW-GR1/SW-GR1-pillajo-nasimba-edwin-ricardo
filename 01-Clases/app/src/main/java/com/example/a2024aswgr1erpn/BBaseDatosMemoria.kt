package com.example.a2024aswgr1erpn

class BBaseDatosMemoria {
    //Companion object

    companion object{
        val arregloBEntrenador = arrayListOf<BEntrenador>()
        init {
            arregloBEntrenador.add(BEntrenador(1, "Edwin", "eyn@viz.com"))
            arregloBEntrenador.add(BEntrenador(2, "Jesus", "jyn@viz.com"))
            arregloBEntrenador.add(BEntrenador(3, "Martin", "myn@viz.com"))

        }
    }
}