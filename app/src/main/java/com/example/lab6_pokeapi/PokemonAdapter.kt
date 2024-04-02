package com.example.lab6_pokeapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class PokemonAdapter(private val pokemonList: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        val pokedexTextView: TextView = itemView.findViewById(R.id.pokedexTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.textView.text = pokemon.name
        holder.typeTextView.text = pokemon.type.joinToString("/")
        holder.pokedexTextView.text = "No. ${pokemon.id}"
        Picasso.get().load(pokemon.imageUrl).into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
