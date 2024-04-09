package com.example.lab6_pokeapi

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import java.util.Locale
import android.text.SpannableStringBuilder


class PokemonAdapter(private val context: Context, private val pokemonList: List<Pokemon>) : RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      val view = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false)
      return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView: TextView = itemView.findViewById(R.id.textView)
        val typeTextView: TextView = itemView.findViewById(R.id.typeTextView)
        val pokedexTextView: TextView = itemView.findViewById(R.id.pokedexTextView)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.textView.text = pokemon.name
        setTypeTextView(holder.typeTextView, pokemon.type)
        holder.pokedexTextView.text = "No. ${pokemon.id}"
        Picasso.get().load(pokemon.imageUrl).into(holder.imageView)
    }

    private fun setTypeTextView(typeTextView: TextView, type: List<String>) {
        val builder = SpannableStringBuilder()
        for (i in type.indices) {
            val typeName = type[i]
            val spannableString = SpannableString(typeName)
            val colorID = when (typeName.toLowerCase(Locale.ROOT)) {
                "dark" -> R.color.dark
                "fighting" -> R.color.fighting
                "fire" -> R.color.fire
                "electric" -> R.color.electric
                "ground" -> R.color.ground
                "rock" -> R.color.rock
                "bug" -> R.color.bug
                "grass" -> R.color.grass
                "ice" -> R.color.ice
                "flying" -> R.color.flying
                "water" -> R.color.water
                "ghost" -> R.color.ghost
                "dragon" -> R.color.dragon
                "poison" -> R.color.poison
                "psychic" -> R.color.psychic
                "fairy" -> R.color.fairy
                "normal" -> R.color.normal
                "steel" -> R.color.steel
                else -> android.R.color.black
            }
            val color = ContextCompat.getColor(typeTextView.context, colorID)
            spannableString.setSpan(
                ForegroundColorSpan(color),
                0, spannableString.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            builder.append(spannableString)
            if (i < type.size - 1) {
                builder.append("/")
            }
        }
        typeTextView.text = builder
    }

    override fun getItemCount(): Int {
        return pokemonList.size
    }
}
