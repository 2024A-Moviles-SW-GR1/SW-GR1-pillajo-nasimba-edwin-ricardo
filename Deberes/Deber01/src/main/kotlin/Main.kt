import controlador.ClienteController
import controlador.PedidoController
import entidades.Cliente
import entidades.Pedido
import java.util.*

fun main(args: Array<String>) {

    val scanner = Scanner(System.`in`)
    val clienteController = ClienteController()
    val pedidoController = PedidoController()

    while (true) {
        println(
            "\n• Cliente - Pedido • CRUD \n" +
                    "1. CRUD Cliente\n" +
                    "2. CRUD Pedido\n" +
                    "3. Salir"
        )
        print("Ingresa una opción: ")
        when (scanner.nextInt()) {
            (1) -> {
                while (true) {
                    println(
                        "\n• Cliente • CRUD\n" +
                                "1. Agregar Cliente\n" +
                                "2. Leer todos los clientes\n" +
                                "3. Leer Pedidos asociados a Cliente por ID\n" +
                                "4. Actualizar telefono del Cliente\n" +
                                "5. Eliminar Cliente\n" +
                                "6. Regresar al menú principal"
                    )
                    print("Ingresa una opción: ")
                    when (scanner.nextInt()) {
                        (1) -> {
                            print("Ingresa el nombre del cliente: ")
                            scanner.nextLine()
                            val nombreCliente = scanner.nextLine()
                            print("Ingresa el email del cliente: ")
                            val emailCliente = scanner.next()
                            print("Ingresa el telefono del cliente: ")
                            val telefonoCliente = scanner.next()
                            print("Ingresa el estado civil del cliente (Casado:C,Soltero:S, Divorciado:D, Viudo:V): ")
                            val estadoCivilCliente = scanner.next()[0]
                            print("Ingresa la edad del cliente: ")
                            val edadCliente = scanner.nextInt()
                            clienteController.agregarCliente(
                                Cliente(
                                    nombre = nombreCliente,
                                    email = emailCliente,
                                    telefono = telefonoCliente,
                                    estadoCivil = estadoCivilCliente,
                                    edad = edadCliente
                                )
                            )
                        }
                        (2) -> {
                            val listaClientes = clienteController.listarClientes()
                            println("\t\t\t\t\t--•-- Lista de Clientes --•--")
                            listaClientes.forEach { println("$it") }
                        }
                        (3) -> {
                            print("Ingresa el id del cliente a buscar: ")
                            val idCliente = scanner.nextInt()
                            val pedidosCliente = clienteController.listarPedidosClientePorID(idCliente)
                            if (pedidosCliente.isNotEmpty()) {
                                println("\t\t\t\t\t--•-- Pedidos del cliente con ID: $idCliente --•--")
                                println(pedidosCliente)
                            } else {
                                println("No se han encontrado pedidos asociados al cliente...")
                            }
                        }
                        (4) -> {
                            print("Ingresa el id del cliente a actualizar: ")
                            val idCliente = scanner.nextInt()
                            val cliente = clienteController.listarClientePorID(idCliente)
                            if (cliente != null) {
                                print("Ingresa el telefono del cliente: ")
                                val telefonoCliente = scanner.next()
                                clienteController.actualizarCliente(cliente, telefonoCliente)
                            } else {
                                println("No encontrado...")
                            }
                        }
                        (5) -> {
                            print("Ingresa el id del cliente a eliminar: ")
                            val idCliente = scanner.nextInt()
                            val cliente = clienteController.listarClientePorID(idCliente)
                            val tienePedidos = clienteController.verificarPedidos(idCliente)
                            if (cliente != null) {
                                if (!tienePedidos) {
                                    clienteController.eliminarCliente(cliente)
                                } else {
                                    println("No se puede eliminar un cliente con pedidos realizados.")
                                }
                            } else {
                                println("No encontrado...")
                            }
                        }
                        (6) -> {
                            println("Regresando al menú principal...")
                            break
                        }
                        else -> {
                            println("Ingresa una opción correcta")
                            continue
                        }
                    }
                }
            }
            (2) -> {
                while (true) {
                    println(
                        "\n• Pedido • CRUD\n" +
                                "1. Agregar Pedido\n" +
                                "2. Leer todos los pedidos\n" +
                                "3. Leer Pedido por ID\n" +
                                "4. Actualizar Pedido\n" +
                                "5. Eliminar Pedido\n" +
                                "6. Regresar al menú principal"
                    )
                    print("Ingresa una opción: ")
                    when (scanner.nextInt()) {
                        (1) -> {
                            print("Ingresa la descripcion del pedido: ")
                            scanner.nextLine()
                            val descripcion = scanner.nextLine()
                            print("Ingresa la cantidad del pedido: ")
                            val cantidad = scanner.nextInt()
                            print("Ingresa el precio unitario: ")
                            val precioU = scanner.nextDouble()
                            print("Ingresa la fecha del pedido en formato dia-mes-año: ")
                            val fecha = scanner.next()
                            print("Ingresa el estado del pedido (Completado:C, En proceso:E, Fallido:F) ")
                            val estado = scanner.next()[0]
                            pedidoController.agregarPedido(
                                Pedido(
                                    descripcion = descripcion,
                                    cantidad = cantidad,
                                    precioUnitario = precioU,
                                    estado = estado
                                ), fecha
                            )

                        }
                        (2) -> {
                            val listaPedidos = pedidoController.listarPedidos()
                            println("\t\t\t\t\t--•-- Lista de Pedidos --•--")
                            listaPedidos.forEach { println("$it") }
                        }
                        (3) -> {
                            print("Ingresa el id del pedido a buscar: ")
                            val idPedido = scanner.nextInt()
                            val pedido = pedidoController.listarPedidoPorID(idPedido)
                            if (pedido != null) {
                                println("\t\t\t\t\t--•-- Pedido con ID: $idPedido --•--")
                                println(pedido)
                            } else {
                                println("No encontrado...")
                            }
                        }
                        (4) -> {
                            println(
                                "1. Asignar Cliente\n" +
                                        "2. Cambiar Precio"
                            )
                            when (scanner.nextInt()) {
                                (1) -> {
                                    print("Ingresa el id del pedido a actualizar: ")
                                    val idPedido = scanner.nextInt()
                                    val pedido = pedidoController.listarPedidoPorID(idPedido)
                                    if (pedido != null) {
                                        print("Ingresa el ID del cliente para el pedido: ")
                                        val idCliente = scanner.nextInt()
                                        pedidoController.actualizarPedido(pedido, idCliente)
                                    } else {
                                        println("No encontrado...")
                                    }
                                }
                                (2) -> {
                                    print("Ingresa el id del pedido a actualizar: ")
                                    val idPedido = scanner.nextInt()
                                    val pedido = pedidoController.listarPedidoPorID(idPedido)
                                    if (pedido != null) {
                                        print("Ingresa el nuevo precio del pedido: ")
                                        val precio = scanner.nextDouble()
                                        pedidoController.actualizarPrecioPedido(pedido, precio)
                                    } else {
                                        println("No encontrado...")
                                    }
                                }
                                else -> {
                                    continue
                                }
                            }
                        }
                        (5) -> {
                            print("Ingresa el id del pedido a eliminar: ")
                            val idPedido = scanner.nextInt()
                            val pedido = pedidoController.listarPedidoPorID(idPedido)
                            if (pedido != null) {
                                pedidoController.eliminarPedido(pedido)
                            } else {
                                println("No encontrado...")
                            }
                        }
                        (6) -> {
                            println("Regresando al menú principal...")
                            break
                        }
                        else -> {
                            println("Ingresa una opción correcta")
                            continue
                        }
                    }
                }
            }
            (3) -> {
                println("Saliendo....")
                break
            }
            else -> {
                println("Ingresa una opción correcta")
                continue
            }
        }
    }
    scanner.close()
}