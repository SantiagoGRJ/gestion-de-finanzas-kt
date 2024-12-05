package com.equipodosb.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.firestore.FirebaseFirestore

class MainGastos : AppCompatActivity() {

    private lateinit var txtMontoTotal: TextView
    private lateinit var btnActualizar: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_gastos)

        // Inicializar vistas
        txtMontoTotal = findViewById(R.id.txtMontoTotal)
        btnActualizar = findViewById(R.id.btnActualizar)

        // Configurar botón para actualizar
        btnActualizar.setOnClickListener {
            obtenerYSumarMontos()
        }

        // Llamar la función al iniciar
        obtenerYSumarMontos()

        // Manejar padding para bordes del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun obtenerYSumarMontos() {
        db.collection("Products")
            .get()
            .addOnSuccessListener { documents ->
                var sumaTotal = 0.0

                for (document in documents) {
                    val monto = document.getDouble("Monto") ?: 0.0
                    sumaTotal += monto
                }

                // Actualizar TextView con la suma total
                txtMontoTotal.text = String.format("%.2f", sumaTotal)
            }
            .addOnFailureListener { exception ->
                // Mostrar mensaje de error si no se puede obtener los datos
                txtMontoTotal.text = "Error al obtener datos"
            }
    }
}
