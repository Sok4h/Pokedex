package com.sokah.pokedex.view

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.sokah.pokedex.PokemonActivity
import com.sokah.pokedex.databinding.PokemonCardBinding
import com.sokah.pokedex.model.Pokemon
import com.sokah.pokedex.model.getDateParse

class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private lateinit var pokemon: Pokemon
    private val binding = PokemonCardBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bindPokemon(pokemon: Pokemon){
        this.pokemon = pokemon

        binding.tvPokemonId.text = "#${pokemon.id}"
        binding.tvPokemonName.text = pokemon.name
        Glide.with(itemView).load(pokemon.sprites.other.artwork.front_default).into(binding.imgPokemonPhoto)

        binding.tvDate.text = getDateParse(pokemon)

        binding.root.setOnClickListener {
            val gson = Gson()

            val intent = Intent(itemView.context,PokemonActivity::class.java)
            intent.putExtra("isMy", true)
            intent.putExtra("uid", pokemon.uid)
            intent.putExtra("pokemon", gson.toJson(pokemon))

            itemView.context.startActivity(intent)
        }
    }
}