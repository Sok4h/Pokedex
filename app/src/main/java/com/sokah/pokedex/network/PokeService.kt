package com.sokah.pokedex.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class PokeService {

    val retrofit = RetrofitHelper.getRetrofit()

    suspend fun findPokemon(pokemon: String) {
        Log.e("TAG", "findPokemon: ",)


        try {

            withContext(Dispatchers.Main) {
                val response = retrofit.create(PokeApi::class.java).searchPokemon(pokemon)
                Log.e("TAG", response.body().toString())
            }

        } catch (e: IOException) {

            Log.e("TAG", e.message.toString() )
        }


    }
}
