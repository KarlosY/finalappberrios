package com.example.practica_ec3


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_ec3.model.Perro


class FavoritoAdapter(private var favoritos: List<Perro>, private val listener: FavoritoItemClickListener) : RecyclerView.Adapter<FavoritoAdapter.FavoritoViewHolder>() {

    interface FavoritoItemClickListener {
        fun onEliminarFavoritoClick(perroId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_favorito, parent, false)
        return FavoritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritoViewHolder, position: Int) {
        val perro = favoritos[position]
        holder.bind(perro)
    }

    override fun getItemCount(): Int = favoritos.size

    fun setData(newFavoritos: List<Perro>) {
        favoritos = newFavoritos
        notifyDataSetChanged()
    }

    inner class FavoritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val breedGroupTextView: TextView = itemView.findViewById(R.id.breedGroupTextView)
        private val lifeSpanTextView: TextView = itemView.findViewById(R.id.lifeSpanTextView)
        private val temperamentTextView: TextView = itemView.findViewById(R.id.temperamentTextView)
        private val originTextView: TextView = itemView.findViewById(R.id.originTextView)
        private val btnEliminarFavorito: Button = itemView.findViewById(R.id.btn_eliminar_favoritos)

        init {
            btnEliminarFavorito.setOnClickListener {
                val perro = favoritos[adapterPosition]
                listener.onEliminarFavoritoClick(perro.id)
            }
        }

        fun bind(perro: Perro) {
            nameTextView.text = perro.name
            breedGroupTextView.text = perro.breed_group
            lifeSpanTextView.text = perro.life_span
            temperamentTextView.text = perro.temperament
            originTextView.text = perro.origin
        }
    }
}
