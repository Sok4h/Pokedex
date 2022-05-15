package com.sokah.pokedex.view

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
    }
}