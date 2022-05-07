package com.sokah.pokedex.network

import com.sokah.pokedex.model.Pokemon
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface PokeApi {

    @GET("pokemon/{pokemon}")

    suspend fun searchPokemon(

    @Path("pokemon") pokemon: String
    ):Response<Pokemon>
}