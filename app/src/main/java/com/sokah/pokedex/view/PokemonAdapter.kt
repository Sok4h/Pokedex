package com.sokah.pokedex.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sokah.pokedex.R
import com.sokah.pokedex.model.*
import java.util.*
import kotlin.collections.ArrayList

class PokemonAdapter(private val username: String) : RecyclerView.Adapter<PokemonViewHolder>() {
    private val pokemons = ArrayList<Pokemon>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        //XML -> View
        val view = inflater.inflate(R.layout.pokemon_card, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bindPokemon(pokemons[position], username)
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