package com.example.practica_ec3.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.practica_ec3.ApiUtils
import com.example.practica_ec3.ApiUtils.createDogApiService
import com.example.practica_ec3.DogApiService
import com.example.practica_ec3.R
import com.example.practica_ec3.databinding.FragmentPerroDetalleBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PerroDetalleFragment : Fragment() {
    private lateinit var binding: FragmentPerroDetalleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPerroDetalleBinding.inflate(inflater, container, false)
        val view = binding.root
        val perroId = arguments?.getInt("perroId") ?: -1 // Obtén el ID del perro desde los argumentos
        // Aquí debes cargar la información del perro correspondiente al perroId desde tu fuente de datos
        val service = createDogApiService()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val perro = service.getPerroDetails(perroId) // Agrega un método para obtener los detalles de un perro por su ID en DogApiService
                withContext(Dispatchers.Main) {
                    // Asigna los detalles del perro a las vistas en el binding
                    binding.nameTextView.text = perro.name
                    binding.breedGroupTextView.text = perro.breed_group
                    binding.lifeSpanTextView.text = perro.life_span
                    binding.temperamentTextView.text = perro.temperament
                    binding.originTextView.text = perro.origin
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val agregarFavoritosButton = view.findViewById<Button>(R.id.btn_agregarFavoritos)

        agregarFavoritosButton.setOnClickListener {
            // Agregar lógica para guardar el perro actual en favoritos usando SharedPreferences
            val sharedPreferences = requireContext().getSharedPreferences("Favoritos", Context.MODE_PRIVATE)

            // Agregar el ID del perro a la lista de favoritos en SharedPreferences
            val editor = sharedPreferences.edit()
            val favoritosSet = sharedPreferences.getStringSet("favoritosSet", mutableSetOf()) ?: mutableSetOf()
            favoritosSet.add(perroId.toString())
            editor.putStringSet("favoritosSet", favoritosSet)
            editor.apply()

            Toast.makeText(requireContext(), "Perro agregado a favoritos", Toast.LENGTH_SHORT).show()
        }

        return view
    }
    val service = ApiUtils.createDogApiService()
}




