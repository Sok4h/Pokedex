package com.sokah.pokedex.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sokah.pokedex.R
import com.sokah.pokedex.model.*

class PokemonAdapter : RecyclerView.Adapter<PokemonViewHolder>() {
    private val pokemons = ArrayList<Pokemon>()

    init {
        val sprites = Sprites(Other(Artwork("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/home/25.png")))
        val testPokemon = Pokemon("pikachu",23,1,34, stats = ArrayList(), sprites)
        pokemons.add(testPokemon)
        pokemons.add(testPokemon)
        pokemons.add(testPokemon)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //XML -> View
        val view = inflater.inflate(R.layout.pokemon_card, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindPokemon(pokemons[position])
    }

    override fun getItemCount(): Int {
        return  pokemons.size
    }

    fun addPokemon(pokemon: Pokemon){
        pokemons.add(pokemon)
        notifyItemInserted(pokemons.size-1)
    }

    fun clear(){
        val size = pokemons.size
        pokemons.clear()
        notifyItemRangeRemoved(0,size)
    }
}