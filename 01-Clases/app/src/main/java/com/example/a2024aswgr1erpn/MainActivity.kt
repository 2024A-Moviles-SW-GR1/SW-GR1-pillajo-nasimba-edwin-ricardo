package com.example.a2024aswgr1erpn

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.ContactsContract.Contacts
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.withStyledAttributes
import com.google.android.material.snackbar.Snackbar
import java.net.URI

class MainActivity : AppCompatActivity() {
    fun mostrarSnackbar(texto:String){
        val snack = Snackbar.make(
            findViewById(R.id.id_layout_main),
            texto,
            Snackbar.LENGTH_INDEFINITE
        )
        snack.show()
    }

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //logica de negocio
                    val data = result.data
                    mostrarSnackbar("${data?.getStringExtra("nombreModificado")}")
                }
            }
        }

    val callbackContenidoIntentImplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
                result ->
            if(result.resultCode == Activity.RESULT_OK){
                if(result.data != null){
                    //logica de negocio
                    if(result.data!!.data != null){
                        val uri:Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri,null,null,null,null,null
                        )

                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono = cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        mostrarSnackbar("Teléfono: ${telefono}")
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val botonCicloVida = findViewById<Button>(R.id.btn_ciclo_vida)
        botonCicloVida.setOnClickListener{
            irActividad(ACicloVida::class.java)
        }

        val botonIrListView = findViewById<Button>(R.id.btn_ir_list_view)
        botonIrListView.setOnClickListener{
            irActividad(BListView::class.java)
        }

        val botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)
        botonIntentImplicito.setOnClickListener{
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackContenidoIntentImplicito.launch(intentConRespuesta)
        }

        val botonIntentExplicito = findViewById<Button>(R.id.btn_ir_intent_explicito)
        botonIntentExplicito.setOnClickListener{
            val intentExplicito = Intent(
                this,
                CIntentExplicitoParametros::class.java
            )
            intentExplicito.putExtra("nombre", "Edwin")
            intentExplicito.putExtra("apellido", "Pillajo")
            intentExplicito.putExtra("edad", "22")
            intentExplicito.putExtra("entrenador",
                                        BEntrenador(7, "Patricio", "Estrella"))
            callbackContenidoIntentExplicito.launch(intentExplicito)
        }

        //Inicializar la base de datos
        EBaseDeDatos.tablaEntrenador = ESqliteHelperEntrenador(this)
        val botonSQLite = findViewById<Button>(R.id.btn_sqlite)
        botonSQLite.setOnClickListener {
            irActividad(ECrudEntrenador::class.java)
        }

    }

    fun irActividad(clase: Class<*>) {
        val intent = Intent(this, clase)
        startActivity(intent)
    }
}