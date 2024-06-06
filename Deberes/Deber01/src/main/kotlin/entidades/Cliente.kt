package entidades


class Cliente(var idCliente: Int?=null,
              val nombre: String,
              val email: String,
              var telefono: String,
              val estadoCivil: Char,
              val edad: Int)
{
    fun setId(id: Int) {
        idCliente = id
    }
    fun seTelefono(telefonoCliente: String) {
        telefono = telefonoCliente
    }
    override fun toString(): String {
        return "ID=$idCliente, Nombre=$nombre, email=$email, Teléfono=$telefono, Estado Civil=$estadoCivil, Edad=$edad"
    }
}