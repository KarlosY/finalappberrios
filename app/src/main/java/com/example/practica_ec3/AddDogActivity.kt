package com.example.practica_ec3

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.practica_ec3.databinding.ActivityAddDogBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class AddDogActivity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST = 1
    private lateinit var imageViewDog: ImageView
    private lateinit var selectedImageUri: Uri
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_dog)

        FirebaseApp.initializeApp(this) // Inicializa Firebase
        db = FirebaseFirestore.getInstance() // Obtiene la instancia de Firestore

        imageViewDog = findViewById(R.id.imageViewDog)
        imageViewDog.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val btnSave = findViewById<Button>(R.id.btn_guardar)
        btnSave.setOnClickListener {
            // Subir la imagen al Firebase Storage y obtener la URL de descarga
            val storage = FirebaseStorage.getInstance()
            val storageRef = storage.reference
            val imageRef = storageRef.child("perroimagen/${UUID.randomUUID()}.jpg")

            imageRef.putFile(selectedImageUri)
                .addOnSuccessListener { taskSnapshot ->
                    // Obtener la URL de descarga de la imagen
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()

                        // Obtener los otros valores ingresados por el usuario
                        val name = findViewById<EditText>(R.id.editTextName).text.toString()
                        val breedGroup = findViewById<EditText>(R.id.editTextBreed_group).text.toString()
                        val lifeSpan = findViewById<EditText>(R.id.editTextLife_span).text.toString()
                        val temperament = findViewById<EditText>(R.id.editTextTemperament).text.toString()
                        val origin = findViewById<EditText>(R.id.editTextOrigin).text.toString()

                        // Crea un mapa con los valores para el documento
                        val dogData = hashMapOf(
                            "name" to name,
                            "breedGroup" to breedGroup,
                            "lifeSpan" to lifeSpan,
                            "temperament" to temperament,
                            "origin" to origin,
                            "imgUrl" to imageUrl // Agrega la URL de la imagen
                        )

                        // Agregar un nuevo documento a la colección "dog" con los valores ingresados
                        db.collection("d0g")
                            .add(dogData)
                            .addOnSuccessListener {
                                // Éxito al guardar
                                Toast.makeText(this, "Dog guardado exitosamente", Toast.LENGTH_SHORT).show()
                                finish() // Cierra la actividad después de guardar
                            }
                            .addOnFailureListener {
                                // Error al guardar
                                Toast.makeText(this, "Error al guardar el Dog", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener {
                    // Error al subir la imagen
                    Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data!!
            imageViewDog.setImageURI(selectedImageUri)
        }
    }
}
