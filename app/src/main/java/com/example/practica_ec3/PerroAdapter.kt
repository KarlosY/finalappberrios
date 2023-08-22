package com.example.practica_ec3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_ec3.fragments.HomeFragment
import com.example.practica_ec3.fragments.PerroDetalleFragment
import com.example.practica_ec3.model.Perro


class PerroAdapter(private var perros: List<Perro>, private val homeFragment: HomeFragment) : RecyclerView.Adapter<PerroAdapter.PerroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerroViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_perro, parent, false)
        return PerroViewHolder(view)
    }

    override fun onBindViewHolder(holder: PerroViewHolder, position: Int) {
        val perro = perros[position]
        holder.bind(perro)

        // Agregar un escuchador de clics al itemView (elemento de la vista)
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("perroId", perro.id) // Pasar el ID del perro como argumento
            val detalleFragment = PerroDetalleFragment()
            detalleFragment.arguments = bundle

            val fragmentManager = homeFragment.parentFragmentManager
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, detalleFragment) // Reemplaza R.id.fragment_container con el ID del contenedor donde deseas mostrar el detalle
                .addToBackStack(null)
                .commit()
        }
    }

    override fun getItemCount(): Int = perros.size

    fun setData(newPerros: List<Perro>) {
        perros = newPerros
        notifyDataSetChanged()
    }

    inner class PerroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val breedGroupTextView: TextView = itemView.findViewById(R.id.breedGroupTextView)
        private val lifeSpanTextView: TextView = itemView.findViewById(R.id.lifeSpanTextView)
        private val temperamentTextView: TextView = itemView.findViewById(R.id.temperamentTextView)
        private val originTextView: TextView = itemView.findViewById(R.id.originTextView)

        fun bind(perro: Perro) {
            nameTextView.text = perro.name
            breedGroupTextView.text = perro.breed_group
            lifeSpanTextView.text = perro.life_span
            temperamentTextView.text = perro.temperament
            originTextView.text = perro.origin
        }
    }
}

