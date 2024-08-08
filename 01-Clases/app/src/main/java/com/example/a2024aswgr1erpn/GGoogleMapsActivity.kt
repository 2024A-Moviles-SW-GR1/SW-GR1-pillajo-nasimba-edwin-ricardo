package com.example.a2024aswgr1erpn

import android.content.pm.PackageManager
import android.icu.text.CaseMap.Title
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.snackbar.Snackbar

class GGoogleMapsActivity : AppCompatActivity() {
    private lateinit var mapa: GoogleMap
    var permisos = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ggoogle_maps)
        solicitarPermisos()
        iniciarLogicaMapa()
        val botonCarolina = findViewById<Button>(R.id.btn_ir_carolina)
        botonCarolina.setOnClickListener {
            val carolina = LatLng(-0.18400026486129287, -78.48445162360261)
            val zoom = 17F
            moverCamaraConZoom(carolina,zoom)
        }

    }
    fun solicitarPermisos(){
        val contexto = this.applicationContext
        val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
        val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
        val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
        val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
        val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                permisoCoarse == PackageManager.PERMISSION_GRANTED
        if(tienePermisos){
            permisos = true
        }else{
            ActivityCompat.requestPermissions(
                this, arrayOf(nombrePermisoFine,nombrePermisoCoarse), 1
            )
        }
    }

    fun iniciarLogicaMapa(){
        val fragmentoMapa = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        fragmentoMapa.getMapAsync {
            googleMap -> with(googleMap){
                mapa = googleMap
                establecerConfiguracionMapa()
                moverQuicentro()
                anadirPoliLinea()
                anadirPoligono()
                escucharListeners()
        }
        }
    }

    private fun escucharListeners() {
        mapa.setOnPolygonClickListener {
            mostrarSnackbar("setOnPolygonClickListener ${it.tag}")
        }
        mapa.setOnPolylineClickListener {
            mostrarSnackbar("setOnPolylineClickListener ${it.tag}")
        }
        mapa.setOnMarkerClickListener {
            mostrarSnackbar("setOnMarkerClickListener ${it.tag}")
            return@setOnMarkerClickListener true
        }
        mapa.setOnCameraMoveListener {
            mostrarSnackbar("setOnCameraMoveListener")
        }
        mapa.setOnCameraMoveStartedListener {
           mostrarSnackbar("setOnCameraMoveStartedListener")
        }
    }

    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(
            findViewById(R.id.cl_google_maps),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    private fun establecerConfiguracionMapa() {
        val contexto = this.applicationContext
        with(mapa){
            val nombrePermisoFine = android.Manifest.permission.ACCESS_FINE_LOCATION
            val nombrePermisoCoarse = android.Manifest.permission.ACCESS_COARSE_LOCATION
            val permisoFine = ContextCompat.checkSelfPermission(contexto, nombrePermisoFine)
            val permisoCoarse = ContextCompat.checkSelfPermission(contexto, nombrePermisoCoarse)
            val tienePermisos = permisoFine == PackageManager.PERMISSION_GRANTED &&
                    permisoCoarse == PackageManager.PERMISSION_GRANTED
            if(tienePermisos){
                mapa.isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }
            uiSettings.isZoomControlsEnabled = true
        }
    }

    private fun anadirMarcador(latLang : LatLng, title: String): Marker{
        return mapa.addMarker(
            MarkerOptions().position(latLang).title(title)
        )!!
    }

    private fun moverCamaraConZoom(latLang: LatLng, zoom: Float = 10F){
        mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(latLang,zoom))
    }

    private fun moverQuicentro(){
        val zoom = 17f
        val quicentro = LatLng(-0.1755181190138262, -78.47918808450619)
        val titulo = "Quicentro"
        val markQuicentro = anadirMarcador(quicentro, titulo)
        markQuicentro.tag = titulo
        moverCamaraConZoom(quicentro, zoom)
    }

    private fun anadirPoliLinea(){
        with(mapa){
            val poliLineaUno = mapa.addPolyline(
                PolylineOptions()
                    .clickable(true)
                    .add(
                        LatLng(-0.17510537390234152, -78.48103949269778),
                        LatLng(-0.17650011599737797, -78.48505207713029),
                        LatLng(-0.1760816933798145, -78.48596402813769)
                    )
            )
            poliLineaUno.tag = "Polilinea - uno"
        }
    }

    private fun anadirPoligono(){
        with(mapa){
            val poligonoUno = mapa.addPolygon(
                PolygonOptions().clickable(true)
                    .add(
                        LatLng(-0.15828793937417673, -78.49798082488444),
                        LatLng(-0.1576012964886818, -78.49780916351834),
                        LatLng(-0.14301012997805912, -78.47669481548839)
                    ))
        }
    }
}