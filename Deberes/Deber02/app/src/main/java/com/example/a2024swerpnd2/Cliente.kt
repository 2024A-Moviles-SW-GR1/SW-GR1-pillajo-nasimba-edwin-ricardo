package com.example.a2024swerpnd2

import android.os.Parcel
import android.os.Parcelable

class Cliente (val idCliente: Int,
               var nombre: String,
               var email: String,
               var telefono: String,
               var estadoCivil: Char,
               var edad: Int):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt().toChar(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idCliente)
        parcel.writeString(nombre)
        parcel.writeString(email)
        parcel.writeString(telefono)
        parcel.writeString(estadoCivil.toString()[0].toString())
        parcel.writeInt(edad)
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