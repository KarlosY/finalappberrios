package com.example.practica_ec3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.practica_ec3.databinding.ActivityRegistrarseBinding

import com.google.firebase.auth.FirebaseAuth

class RegistrarseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarseBinding
    private lateinit var auth: FirebaseAuth // Agregar esta variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance() // Inicializar FirebaseAuth

        binding.btnRegister.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                registerWithEmailAndPassword(email, password)
            } else {
                // Manejar errores si los campos están vacíos
            }
        }
    }

    private fun registerWithEmailAndPassword(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registro exitoso
                        val user = auth.currentUser
                        //  mostrar un mensaje de éxito
                        Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show()
                        // Redirigir a LoginActivity
                        val loginIntent = Intent(this, LoginActivity::class.java)
                        startActivity(loginIntent)
                        finish() //  finalizar esta actividad de registro
                    } else {
                        // El registro falló
                        val errorMessage = task.exception?.message ?: "Error al registrarte"
                        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
        } else {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
        }
    }
}
