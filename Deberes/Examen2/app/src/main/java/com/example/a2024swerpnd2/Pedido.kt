package com.example.a2024swerpnd2

import android.os.Parcel
import android.os.Parcelable

class Pedido(val idPedido:Int,
             var descripcion: String,
             var cantidad: Int,
             var precioUnitario: Double,
             var estado: Char,
             val idCliente: Int): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readInt().toChar(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idPedido)
        parcel.writeString(descripcion)
        parcel.writeInt(cantidad)
        parcel.writeDouble(precioUnitario)
        parcel.writeString(estado.toString()[0].toString())
        parcel.writeInt(idCliente)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$idPedido - $descripcion - $cantidad - $precioUnitario"
    }

    companion object CREATOR : Parcelable.Creator<Pedido> {
        override fun createFromParcel(parcel: Parcel): Pedido {
            return Pedido(parcel)
        }

        override fun newArray(size: Int): Array<Pedido?> {
            return arrayOfNulls(size)
        }
    }

}