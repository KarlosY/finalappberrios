package com.example.practica_ec3.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_ec3.ApiUtils
import com.example.practica_ec3.ApiUtils.createDogApiService
import com.example.practica_ec3.DogApiService
import com.example.practica_ec3.PerroAdapter
import com.example.practica_ec3.R

import com.example.practica_ec3.databinding.FragmentHomeBinding
import com.example.practica_ec3.model.Perro
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: PerroAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.personaslistRecyclerView
        adapter = PerroAdapter(emptyList(), this) // Pasa "this" como listener
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        fetchPerros()
    }

    private fun fetchPerros() {
        val service = createDogApiService()
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val perros = service.getPerros()
                withContext(Dispatchers.Main) {
                    adapter.setData(perros)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
    val service = ApiUtils.createDogApiService()


}


