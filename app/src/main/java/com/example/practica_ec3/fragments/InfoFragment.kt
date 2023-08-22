package com.example.practica_ec3.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.practica_ec3.LoginActivity
import com.example.practica_ec3.databinding.FragmentInfoBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class InfoFragment : Fragment() {
    private lateinit var binding: FragmentInfoBinding
    private var selectedImageUri: Uri? = null
    private val auth = FirebaseAuth.getInstance()
    private val user = auth.currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (user != null) {
            val userEmail = user.email
            binding.txtNameEmail.text = userEmail
        }

        binding.imageViewUsuario.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_PICK_REQUEST_CODE)
        }

        binding.btnCerrar.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val googleSignInClient = GoogleSignIn.getClient(
                requireContext(),
                GoogleSignInOptions.DEFAULT_SIGN_IN
            )
            googleSignInClient.signOut().addOnCompleteListener {
                val sharedPreferences =
                    requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putBoolean("isLoggedIn", false)
                editor.apply()

                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }

        binding.btnSubirImagen.setOnClickListener {
            selectedImageUri?.let { imageUri ->
                uploadImageToStorage(imageUri)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data
            binding.imageViewUsuario.setImageURI(selectedImageUri)
        }
    }

    private fun uploadImageToStorage(imageUri: Uri) {
        val storageRef = FirebaseStorage.getInstance().reference
        val imageRef = storageRef.child("imagen/${user?.uid}.jpg")

        val uploadTask = imageRef.putFile(imageUri)

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let { throw it }
            }
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                Toast.makeText(
                    requireContext(),
                    "Imagen subida correctamente",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Error al subir la imagen",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    companion object {
        private const val IMAGE_PICK_REQUEST_CODE = 100
    }
}