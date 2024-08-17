package com.example.a2024swerpnd2

import android.os.Parcel
import android.os.Parcelable

class Cliente(
    val idCliente: Int,
    var nombre: String,
    var email: String,
    var telefono: String,
    var estadoCivil: Char,
    var edad: Int,
    var latitud: Double = Double.NaN, // Usar Double.NaN para valores no inicializados
    var longitud: Double = Double.NaN // Usar Double.NaN para valores no inicializados
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt().toChar(),
        parcel.readInt(),
        parcel.readDouble(), // Leer latitud
        parcel.readDouble()  // Leer longitud
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idCliente)
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(telefono)
        parcel.writeString(estadoCivil.toString()[0].toString())
        parcel.writeInt(edad)
        parcel.writeDouble(latitud) // Escribir latitud
        parcel.writeDouble(longitud) // Escribir longitud
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$idCliente - $nombre - $email"
    }

    companion object CREATOR : Parcelable.Creator<Cliente> {
        override fun createFromParcel(parcel: Parcel): Cliente {
            return Cliente(parcel)
        }

        override fun newArray(size: Int): Array<Cliente?> {
            return arrayOfNulls(size)
        }
    }
}

