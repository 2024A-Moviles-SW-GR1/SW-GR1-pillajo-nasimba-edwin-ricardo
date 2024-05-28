import java.util.Date

fun main() {
    println("Hola mundo")

    //Variables inmutables (No se RE ASIGNA "=")
    val inmutable: String = "Edwin";

    //Variable mutable  (val > var)
    var mutable: String = "Ricardo";
    mutable = "asr";

    //Duck Typing
    val ejemploVariale = " Edwin Ricardo ";
    ejemploVariale.trim()

    //Variables primitivas: String, Double, Char, Boolean
    val nombre: String = "Edwin";
    val valor: Double = 8.9
    val estadoCivil: Char = 'S'
    val mayorDeEdad: Boolean = true

    //Clases en Java
    val fechaNacimiento: Date = Date()
    println(fechaNacimiento)

    //When (Switch)
    val estadoCivilW = 'S'
    when (estadoCivilW) {
        ('C') -> {
            println("Casado")
        }
        'S' -> {
            println("Soltero")
        }
        else -> {
            println("Desconocido")
        }
    }

    val esSoltero = (estadoCivilW == 'S')
    val coqueteo = if (esSoltero) println("Si") else println("No") //if else pequeño

    calcularSueldo(10.00)
    calcularSueldo(10.00, 15.00, 20.00)

    //Named parameters
    //calcularSueldo(sueldo, tasa, bonoEspecial)
    calcularSueldo(10.00, bonoEspecial = 20.00)
    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    //Uso de clases
    /*val sumaUno = Suma(1,1)//Las instancias en las clases no necesitan la palabra new
    val sumaDos = Suma(null,1)
    val sumaTres = Suma(1, null)
    val sumaCuatro = Suma(null, null)
    sumaUno.sumar()
    sumaDos.sumar()
    sumaTres.sumar()
    sumaCuatro.sumar()

    println(Suma.pi)
    println(Suma.elevarAlCuadrado(2))
    println(Suma.historialSumas)*/

    // Arreglos
    //Existen dos tipos de arreglos estaticos y dinamicos
    //Esteaticos
    val arregloEstatico: Array<Int> = arrayOf<Int>(1,2,3)
    println(arregloEstatico)

    //Dinamicos
    val arregloDinamico: ArrayList<Int> = arrayListOf<Int>(
        1, 2, 3, 4, 5, 6, 7, 8, 9, 10
    )
    println(arregloDinamico)
    arregloDinamico.add(11)
    arregloDinamico.add(12)
    //println(arregloDinamico)

    //Operador FOR EACH => Unit
    val respuestaForEach:Unit = arregloDinamico.forEach{
        valorActual: Int ->
        println("Valor actual: ${valorActual}")
    }

    //"it" (en ingles "eso") significa el elemento iterado
    arregloDinamico.forEach{ println("Valor actual (it): ${it}") }

    //MAP --> Muta(Modifica, cambio) el arreglo
    // 1) Enviamos el nuevo valor
    // 2) Devuelve el nuevo arreglo con los valores de las iteraciones
    val respuestaMap : List<Double> = arregloDinamico
        .map {valorActual: Int ->
        return@map valorActual.toDouble() + 100.0
        }
    println(respuestaMap)

    val respuestaMapDos = arregloDinamico.map { it + 15 }
    println(respuestaMapDos)

    //Filter -> Filtrar el arreglo
    // 1) Devolver una expresion (True o false)
    // 2) Nuevo arreglo filtrado
    val respuestaFilter : List<Int> = arregloDinamico
        .filter { valorActual: Int ->
            //Expresion o condicion
            val mayoresACinco: Boolean = valorActual > 5
            return@filter mayoresACinco
        }

    val respuestaFilterDos = arregloDinamico.filter { it <=5 }
    println(respuestaFilter)
    println(respuestaFilterDos)

    //Operador OR AND
    // OR -> ANY (alguno cumple?)
    // And -> ALL(todos cumplen?)
    val respuestaAny : Boolean = arregloDinamico.any{
        valorActual:Int ->
        return@any (valorActual > 5)
    }
    println(respuestaAny)

    val respuestaAll: Boolean = arregloDinamico.all {
        valorActual: Int ->
        return@all (valorActual>5)
    }
    println(respuestaAll)

    //Reduce -> Valor acumulado
    //Valor acumulado = 0 (siempre empieza en 0 para Kotlin)
    val respuestaReduce : Int = arregloDinamico
        .reduce{
            acumulado:Int, valorActual:Int ->
            return@reduce (acumulado+valorActual)
        }
    println(respuestaReduce)
}

// void -> Unit
fun imprimirNombre(nombre: String): Unit {
    println("Nombre: ${nombre}")
}

fun calcularSueldo(
    sueldo: Double, //Requerido
    tasa: Double = 12.00,   //Opcional (toma el valor por defecto)
    bonoEspecial: Double? = null //Opcional (nullable)
    //Variable? --> "?" Es Nullable (puede que en algun momento ser nula)
): Double {
    // Int --> Int? (nullable)
    // String --> String? (nullable)
    // Date --> Date? (nullable)
    if (bonoEspecial == null) {
        return sueldo * (100 / tasa)
    } else {
        return sueldo * (100 / tasa) * bonoEspecial
    }
}

abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(uno: Int, dos: Int){
        this.numeroUno = uno
        this.numeroDos = dos
        println("Inicializando")
    }
}

abstract class Numeros(//Constructor primario
    //Caso 1) Parametro normal
    //uno: Int , (parametro (sin modificador de acceso))

    //Caso 2) Parametro y modificador (atributo)
    // private var uno: Int (Propiedad instancia.uno)
    protected val numeroUno: Int,
    protected val numeroDos: Int
){
    init { // Bloque de constructor primario OPCIONAL
        this.numeroUno
        this.numeroDos
        println("Inicializando")
    }
}

class Suma(unoParametro: Int,
            dosParametro: Int
):Numeros(/*Clase padre, Numeros (Extendiendo)*/
    unoParametro,
    dosParametro
){
    public val soyPublicoExplicito: String = "Explicito" //Publico
    val soyPublicoImplicito: String = "Implicito"
    init {
        this.numeroUno
        this.numeroDos
        numeroUno //this. Opcional (propiedades, metodos)
        numeroDos //this. Opcional (propiedades, metodos)
        this.soyPublicoExplicito
        soyPublicoImplicito //this. Opcional (propiedades, metodos)
    }

    constructor( //Constructor secundario
        uno:Int?,
        dos:Int
    ):this(
        if (uno == null) 0 else uno,
        dos
    )

    constructor( //Tercer Constructor
        uno:Int,
        dos:Int?
    ):this(
        uno,
        if (dos == null) 0 else dos
    )

    constructor( //Cuarto Constructor
        uno:Int?,
        dos:Int?
    ):this(
        if (uno == null) 0 else uno,
        if (dos == null) 0 else dos
    )

    fun sumar():Int{
        val total = numeroUno + numeroDos
        // Suma.agregarHistorial(total)
        // ("Suma." o "NombreClase." es Opcional)
        agregarHistorial(total)
        return total
    }

    companion object { //Comparte entre todas las instancias
        //Similar al static
        //Funciones y variables
        val pi = 3.14 //Suma.pi

        fun elevarAlCuadrado(num:Int):Int{ //Suma.elevarAlCuadrado
            return num * num;
        }

        val historialSumas = arrayListOf<Int>() //Suma.historialSumas

        fun agregarHistorial(valorSumaTotal:Int){ //Suma.agregarHistorial
            historialSumas.add(valorSumaTotal)
        }
    }
}

