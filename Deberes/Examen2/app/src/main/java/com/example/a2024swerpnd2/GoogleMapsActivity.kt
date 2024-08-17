package com.example.a2024swerpnd2

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar

class GoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    private var ubicacionSeleccionada: LatLng? = null
    private var clienteID: Int = 0
    private var latitud: Double = 0.0
    private var longitud: Double = 0.0
    private var tieneUbicacionCliente: Boolean = false
    private var currentMarker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_maps)

        val intent = intent
        clienteID = intent.getIntExtra("idCliente", -1)
        latitud = intent.getDoubleExtra("latitud", 0.0)
        longitud = intent.getDoubleExtra("longitud", 0.0)

        tieneUbicacionCliente = (latitud != 0.0 && longitud != 0.0)

        solicitarPermisos()
        iniciarLogicaMapa()

        val botonAgregarUbicacion = findViewById<Button>(R.id.btn_agregar_ubicacion_cliente)

        botonAgregarUbicacion.setOnClickListener {
            if (tieneUbicacionCliente) {
                irActividad(MainActivity::class.java)
            } else {
                // Lógica para manejar la ubicación seleccionada por el usuario
                ubicacionSeleccionada?.let { ubicacion ->
                    val respuesta = BaseDeDatos.dbHelper!!.actualizarUbicacionCliente(
                        ubicacionSeleccionada!!.latitude,
                        ubicacionSeleccionada!!.longitude,
                        clienteID
                    )
                    if (respuesta) {
                        irActividad(MainActivity::class.java)
                    } else {
                        mostrarSnackbar("No se ha agregado la ubicación al cliente")
                    }
                } ?: run {
                    mostrarSnackbar("No se ha seleccionado ninguna ubicación.")
                }
            }
        }
    }

    fun solicitarPermisos() {
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if (tienePermisos) {
            permisos = true
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine, nombrePermisoCoarse), 1
            )
        }
    }

    fun iniciarLogicaMapa() {
        val fragmentoMapa = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync { googleMap ->
            with(googleMap) {
                mapa = googleMap
                establecerConfiguracionMapa()
                configurarMapa()
                if (tieneUbicacionCliente) {
                    moverUbicacionCliente()
                } else {
                    moverUbicacionAQuito()
                }
                escucharListeners()
            }
        }
    }

    private fun moverUbicacionAQuito() {
        val zoom = 17f
        val ubicacionQuito =
            LatLng(-0.218530, -78.513207)
        val titulo = "Quito"
        val markQuito = anadirMarcador(ubicacionQuito, titulo)
        markQuito.tag = titulo
        moverCamaraConZoom(ubicacionQuito, zoom)
    }

    private fun configurarMapa() {
        val textoUbicacion = findViewById<TextView>(R.id.selecciona_ubicacion)
        val botonAgregarUbicacion = findViewById<Button>(R.id.btn_agregar_ubicacion_cliente)
        if (tieneUbicacionCliente) {
            textoUbicacion.text = "Tu ubicación"
            botonAgregarUbicacion.text = "Regresar"
        } else {
            textoUbicacion.text = "Selecciona la ubicación"
            botonAgregarUbicacion.text = "Agregar"
        }
    }

    fun escucharListeners() {
        mapa.setOnMapClickListener { latLng ->
            if(!tieneUbicacionCliente){
                ubicacionSeleccionada = latLng
                anadirMarcador(latLng, "Ubicación seleccionada")
            }
        }
    }

    fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa) {
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if (tienePermisos) {
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    fun irActividad(clase: Class<*>, clienteSeleccionado: Cliente? = null) {
        val intent = Intent(this, clase)
        if (clienteSeleccionado != null) {
            intent.putExtra("cliente", clienteSeleccionado)
        }
        startActivity(intent)
    }

    private fun mostrarSnackbar(mensaje: String) {
        val snack = Snackbar.make(
            findViewById(R.id.cl_crear_pedido),
            mensaje,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }


    private fun moverUbicacionCliente() {
        val zoom = 17f
        val ubicacionCliente =
            LatLng(latitud, longitud)
        val titulo = "Mi ubicación"
        val markCliente = anadirMarcador(ubicacionCliente, titulo)
        markCliente.tag = titulo
        moverCamaraConZoom(ubicacionCliente, zoom)
    }

    private fun anadirMarcador(latLng: LatLng, title: String): Marker {
        currentMarker?.remove()
        currentMarker = mapa.addMarker(
            MarkerOptions().position(latLng)
                .title(title)
        )!!
        return currentMarker as Marker
    }

    private fun moverCamaraConZoom(latLng: LatLng, zoom: Float = 10f) {
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
    }
}