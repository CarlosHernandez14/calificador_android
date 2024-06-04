package com.example.calificadormovil

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.calificadormovil.services.LoginRequest
import com.example.calificadormovil.services.LoginResponse
import com.example.calificadormovil.services.RetrofitClient
import com.example.calificadormovil.ui.theme.CalificadorMovilTheme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main);

        // Obtener referencias a los EditText y Button
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        // Configurar el evento onClick del botón
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString()
            val password = etPassword.text.toString()

            // Aquí puedes agregar la lógica de autenticación
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Realizar la petición de autenticación
                val loginRequest = LoginRequest(username, password)
                RetrofitClient.getInstance(this@MainActivity).login(loginRequest).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse != null) {
                                // Guarda el token en SharedPreferences
                                val sharedPreferences = getSharedPreferences("myAppPrefs", MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putString("authToken", loginResponse.token)
                                editor.apply()

                                Toast.makeText(this@MainActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()

                                // Redirige al usuario a otra actividad
                                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                                startActivity(intent)
                                finish() // Finaliza la actividad actual para que no pueda volver con el botón de atrás
                            } else {
                                Toast.makeText(this@MainActivity, "Respuesta vacía del servidor", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Log.e("API_REQUEST_ERROR", "Error en la solicitud: ${response.errorBody()?.string()}")
                            Toast.makeText(this@MainActivity, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Log.e("API_REQUEST_ERROR", "Error de red: ${t.message}", t)
                        Toast.makeText(this@MainActivity, "Error de red: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        }
    }
}