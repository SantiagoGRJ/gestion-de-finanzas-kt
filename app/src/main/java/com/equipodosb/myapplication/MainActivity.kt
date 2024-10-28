package com.equipodosb.myapplication
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import android.app.DatePickerDialog
import java.util.Calendar


class MainActivity : AppCompatActivity(), TuAdapter.OnItemClickListener {

    private val db = FirebaseFirestore.getInstance()
    private val tuColeccion =db.collection("Products")
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TuAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recyclerView = findViewById(R.id.rDatos)
        recyclerView.layoutManager=LinearLayoutManager(this)
        adapter= TuAdapter(this)
        recyclerView.adapter=adapter

        val btnConsultar: Button = findViewById(R.id.btnConsultar)
        val btnInsertar: Button = findViewById(R.id.btnInsertar)
        val btnUpdate: Button = findViewById(R.id.btnActualizar)
        val btnDelete: Button = findViewById(R.id.btnEliminar)
        val txt_Fecha: TextView = findViewById(R.id.txt_Fecha)

// Configurar el evento de clic para el campo de fecha
        txt_Fecha.setOnClickListener {
            // Obtener la fecha actual
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Mostrar el DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    // Formatear la fecha seleccionada y mostrarla en el campo
                    val formattedDate = "${selectedDay.toString().padStart(2, '0')}/" +
                            "${(selectedMonth + 1).toString().padStart(2, '0')}/" +
                            "$selectedYear"
                    txt_Fecha.text = formattedDate
                },
                year, month, day
            )
            datePickerDialog.show()
        }


        btnDelete.setOnClickListener {
            val txt_id: TextView = findViewById(R.id.txt_ID)
            val IDD: String = txt_id.text.toString().trim()

            // Validar que el campo ID no esté vacío
            if (IDD.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa o selecciona un ID válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Intentar eliminar el documento
            tuColeccion.document(IDD)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Eliminación exitosa", Toast.LENGTH_SHORT).show()
                    consultarColeccion()
                    limpiarCajasDeTexto()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al eliminar: ${e.message}", Toast.LENGTH_SHORT).show()
                }

        }

        btnUpdate.setOnClickListener {
            val txt_Fecha: TextView = findViewById(R.id.txt_Fecha)
            val txt_Monto: TextView = findViewById(R.id.txt_Monto)
            val txt_Metodo: TextView = findViewById(R.id.txt_Metodo)
            val txt_Desc: TextView = findViewById(R.id.txt_Desc)
            val txt_id: TextView = findViewById(R.id.txt_ID)

            val fech: String = txt_Fecha.text.toString().trim()
            val montText: String = txt_Monto.text.toString().trim()
            val met: String = txt_Metodo.text.toString().trim()
            val des: String = txt_Desc.text.toString().trim()
            val IDD: String = txt_id.text.toString().trim()

            // Validaciones
            if (IDD.isEmpty()) {
                Toast.makeText(this, "Seleccione Un Registro.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (fech.isEmpty() || montText.isEmpty() || met.isEmpty() || des.isEmpty()) {
                Toast.makeText(this, "Seleccione Un Registro.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val mont: Int = try {
                montText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "El campo Monto debe ser un número válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Construir el mapa de datos
            val docActualizado = mapOf(
                "Fecha" to fech,
                "Monto" to mont,
                "Metodo" to met,
                "Desc" to des
            )

            // Actualizar en Firebase
            tuColeccion.document(IDD)
                .update(docActualizado)
                .addOnSuccessListener {
                    Toast.makeText(this, "Actualización exitosa", Toast.LENGTH_SHORT).show()
                    consultarColeccion()
                    limpiarCajasDeTexto()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al actualizar: ${e.message}", Toast.LENGTH_SHORT).show()
                }



        }



        btnConsultar.setOnClickListener()
        {
            consultarColeccion()
            limpiarCajasDeTexto()
        }


        btnInsertar.setOnClickListener()
        {
            val txt_Fecha: TextView = findViewById(R.id.txt_Fecha)
            val txt_Monto: TextView = findViewById(R.id.txt_Monto)
            val txt_Metodo: TextView = findViewById(R.id.txt_Metodo)
            val txt_Desc: TextView = findViewById(R.id.txt_Desc)

            val fech: String = txt_Fecha.text.toString().trim()
            val montText: String = txt_Monto.text.toString().trim()
            val met: String = txt_Metodo.text.toString().trim()
            val des: String = txt_Desc.text.toString().trim()

            // Verificar si los campos están vacíos
            if (fech.isEmpty() || montText.isEmpty() || met.isEmpty() || des.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Convertir el monto a Int de forma segura
            val mont: Int = try {
                montText.toInt()
            } catch (e: NumberFormatException) {
                Toast.makeText(this, "El campo Monto debe ser un número válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Crear el HashMap con los datos
            val data = hashMapOf(
                "Fecha" to fech,
                "Monto" to mont,
                "Metodo" to met,
                "Desc" to des
            )

            // Insertar en Firebase
            db.collection("Products")
                .add(data)
                .addOnSuccessListener {
                    Toast.makeText(this, "Registro Exitoso", Toast.LENGTH_SHORT).show()
                    consultarColeccion()
                    limpiarCajasDeTexto()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Error al registrar los datos: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }


    }

    private fun consultarColeccion()
    {
        tuColeccion.get()
            .addOnSuccessListener { querySnapshot ->
                val listaTuModelo = mutableListOf<Products>()
                for (document in querySnapshot)
                {
                    val fecha = document.getString("Fecha")
                    val monto = document.getLong("Monto")?.toInt()
                    val metodo = document.getString("Metodo")
                    val desc = document.getString("Desc")
                    val ID = document.id
                    if (fecha != null && monto != null && metodo != null && desc !=null)
                    {
                        val tuModelo= Products(ID,fecha, monto, metodo, desc)
                        listaTuModelo.add((tuModelo))
                    }

                }
                adapter.setDatos(listaTuModelo)

            }

    }

    override fun onItemClick(tuModelo: Products) {
        val txt_Fecha : TextView = findViewById(R.id.txt_Fecha)
        val txt_Monto : TextView = findViewById(R.id.txt_Monto)
        val txt_Metodo : TextView = findViewById(R.id.txt_Metodo)
        val txt_Desc : TextView = findViewById(R.id.txt_Desc)
        val txt_id : TextView = findViewById(R.id.txt_ID)

        txt_Fecha.text=tuModelo.fecha
        txt_Monto.text=tuModelo.monto.toString()
        txt_Metodo.text=tuModelo.metodo
        txt_Desc.text=tuModelo.desc
        txt_id.text=tuModelo.id


    }

    private fun limpiarCajasDeTexto() {
        val txt_Fecha: TextView = findViewById(R.id.txt_Fecha)
        val txt_Monto: TextView = findViewById(R.id.txt_Monto)
        val txt_Metodo: TextView = findViewById(R.id.txt_Metodo)
        val txt_Desc: TextView = findViewById(R.id.txt_Desc)
        val txt_id: TextView = findViewById(R.id.txt_ID)

        txt_Fecha.text = ""
        txt_Monto.text = ""
        txt_Metodo.text = ""
        txt_Desc.text = ""
        txt_id.text = ""
    }

}