package com.example.practica_ec3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.SharedPreferences
import android.widget.Toast
import com.example.practica_ec3.databinding.ActivityLoginBinding
import android.content.Context

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

        val currentUser = auth.currentUser
        val isGoogleSignedIn = GoogleSignIn.getLastSignedInAccount(this) != null

        if (sharedPreferences.getBoolean("isLoggedIn", false) || currentUser != null || isGoogleSignedIn) {
            // Usuario ya ha iniciado sesión previamente
            val mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finish()
        }else{
            binding.btnGoogle.setOnClickListener {
                signInWithGoogle()
            }
            binding.btnRedirigeRegister.setOnClickListener {
                val registerIntent = Intent(this, RegistrarseActivity::class.java)
                startActivity(registerIntent)
            }
            binding.buttonLogin.setOnClickListener {
                val email = binding.editTextEmail.text.toString()
                val password = binding.editTextPassword.text.toString()

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    signInWithEmailAndPassword(email, password)
                } else {
                    Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso
                    val user = auth.currentUser

                    // Guardar el estado de inicio de sesión en SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putBoolean("isLoggedIn", true)
                    editor.apply()

                    // Iniciar la MainActivity aquí
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                } else {
                    // Inicio de sesión fallido
                    val errorMessage = task.exception?.message ?: "Error al iniciar sesión"
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun signInWithGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            } catch (e: ApiException) {
                // Manejar el error
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // El inicio de sesión con Google fue exitoso
                    val user = auth.currentUser

                    // Iniciar la MainActivity aquí
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish() // Opcional: Puedes finalizar esta actividad de inicio de sesión si no deseas volver a ella
                } else {
                    // El inicio de sesión con Google falló
                    Toast.makeText(this,"Error al iniciar sesion :(", Toast.LENGTH_SHORT).show()
                }
            }
    }


    companion object {
        private const val RC_SIGN_IN = 9001
    }
}
