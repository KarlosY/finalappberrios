package com.example.practica_ec3.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_ec3.DogAdapter
import com.example.practica_ec3.R
import com.example.practica_ec3.databinding.FragmentFirebaseBinding
import com.example.practica_ec3.model.Dog
import com.google.firebase.firestore.FirebaseFirestore


class FirebaseFragment : Fragment() {
    private lateinit var binding: FragmentFirebaseBinding
    private lateinit var dogRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_firebase, container, false)

        dogRecyclerView = view.findViewById(R.id.dog_firebase_RecyclerView)

        // Obtén la instancia de Firestore
        val db = FirebaseFirestore.getInstance()

        // Obtén los documentos de la colección "dog"
        db.collection("d0g")
            .get()
            .addOnSuccessListener { result ->
                val dogList = ArrayList<Dog>() // Crear una lista de objetos Dog

                for (document in result) {
                    val dog = document.toObject(Dog::class.java)
                    dogList.add(dog)
                }

                // Configurar el RecyclerView con un adaptador
                val adapter = DogAdapter(dogList)
                dogRecyclerView.adapter = adapter
                dogRecyclerView.layoutManager = LinearLayoutManager(context)
            }
            .addOnFailureListener { exception ->
//                Log.d(TAG, "Error getting documents: ", exception)
            }

        return view
    }
}