package com.example.practica_ec3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practica_ec3.model.Dog

import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions

class DogAdapter(private val dogList: List<Dog>) :
    RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_perro_firebase, parent, false)
        return DogViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        val currentDog = dogList[position]
        holder.bind(currentDog)
    }

    override fun getItemCount() = dogList.size

    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameText: TextView = itemView.findViewById(R.id.nameText)
        private val breedGroupText: TextView = itemView.findViewById(R.id.breedGroupText)
        private val lifeSpanText: TextView = itemView.findViewById(R.id.lifeSpanText)
        private val temperamentText: TextView = itemView.findViewById(R.id.temperamentText)
        private val originText: TextView = itemView.findViewById(R.id.originText)
        private val imageViewDogs: ImageView = itemView.findViewById(R.id.imageViewDogs)

        fun bind(dog: Dog) {
            nameText.text = dog.name
            breedGroupText.text = dog.breedGroup
            lifeSpanText.text = dog.lifeSpan
            temperamentText.text = dog.temperament
            originText.text = dog.origin

            // Cargar la imagen desde la URL usando Glide
            Glide.with(itemView)
                .load(dog.imgUrl)
                .placeholder(R.drawable.ic_usuario) // Imagen de carga

                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageViewDogs)
        }
    }
}



//class DogAdapter(private val dogList: List<Dog>) :
//    RecyclerView.Adapter<DogAdapter.DogViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
//        val itemView = LayoutInflater.from(parent.context)
//            .inflate(R.layout.item_perro_firebase, parent, false)
//        return DogViewHolder(itemView)
//    }
//
//    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
//        val currentDog = dogList[position]
//        holder.bind(currentDog)
//    }
//
//    override fun getItemCount() = dogList.size
//
//    inner class DogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//
//        private val nameText: TextView = itemView.findViewById(R.id.nameText)
//        private val breedGroupText: TextView = itemView.findViewById(R.id.breedGroupText)
//        private val lifeSpanText: TextView = itemView.findViewById(R.id.lifeSpanText)
//        private val temperamentText: TextView = itemView.findViewById(R.id.temperamentText)
//        private val originText: TextView = itemView.findViewById(R.id.originText)
//
//        fun bind(dog: Dog) {
//            nameText.text = dog.name
//            breedGroupText.text = dog.breedGroup
//            lifeSpanText.text = dog.lifeSpan
//            temperamentText.text = dog.temperament
//            originText.text = dog.origin
//
//        }
//    }
//}
