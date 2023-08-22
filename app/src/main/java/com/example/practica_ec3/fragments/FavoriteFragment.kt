package com.example.practica_ec3.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_ec3.ApiUtils.createDogApiService
import com.example.practica_ec3.FavoritoAdapter

import com.example.practica_ec3.R
import com.example.practica_ec3.databinding.FragmentFavoriteBinding
import com.example.practica_ec3.model.Perro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext



class FavoriteFragment : Fragment(), FavoritoAdapter.FavoritoItemClickListener {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritoAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerView = binding.favoritosRecyclerView
        adapter = FavoritoAdapter(emptyList(), this) // Pasar "this" como listener
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // Obtener los IDs de los perros favoritos desde SharedPreferences
        val sharedPreferences = requireContext().getSharedPreferences("Favoritos", Context.MODE_PRIVATE)
        val favoritosSet = sharedPreferences.getStringSet("favoritosSet", mutableSetOf()) ?: mutableSetOf()

        // Obtener los detalles de los perros favoritos desde la API y actualizar el RecyclerView
        val service = createDogApiService()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val favoritos = mutableListOf<Perro>() // Lista para almacenar los detalles de los favoritos
                for (perroId in favoritosSet) {
                    val perro = service.getPerroDetails(perroId.toInt())
                    favoritos.add(perro)
                }
                withContext(Dispatchers.Main) {
                    adapter.setData(favoritos)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return view
    }

    override fun onEliminarFavoritoClick(perroId: Int) {
        val sharedPreferences = requireContext().getSharedPreferences("Favoritos", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val favoritosSet = sharedPreferences.getStringSet("favoritosSet", mutableSetOf()) ?: mutableSetOf()
        favoritosSet.remove(perroId.toString()) // Remover el perro de la lista de favoritos
        editor.putStringSet("favoritosSet", favoritosSet)
        editor.apply()
        Toast.makeText(requireContext(), "Perro eliminado de favoritos", Toast.LENGTH_SHORT).show()

    }
}


