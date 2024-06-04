package com.example.calificadormovil

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.calificadormovil.services.ApiService
import com.example.calificadormovil.services.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardActivity : ComponentActivity() {
    private lateinit var apiService: ApiService
    private lateinit var rvCursos: RecyclerView
    private lateinit var cursoAdapter: CursoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        rvCursos = findViewById(R.id.rvCursos)
        rvCursos.layoutManager = LinearLayoutManager(this)
        cursoAdapter = CursoAdapter(listOf())
        rvCursos.adapter = cursoAdapter

        apiService = RetrofitClient.getInstance(this)

        // Obtener la lista de cursos desde la API
        apiService.getCursos().enqueue(object : Callback<List<Curso>> {
            override fun onResponse(call: Call<List<Curso>>, response: Response<List<Curso>>) {
                if (response.isSuccessful) {
                    val cursos = response.body()
                    if (cursos != null) {
                        cursoAdapter.updateCursos(cursos)
                    } else {
                        Toast.makeText(this@DashboardActivity, "No se encontraron cursos", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@DashboardActivity, "Error al obtener los cursos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Curso>>, t: Throwable) {
                Toast.makeText(this@DashboardActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
