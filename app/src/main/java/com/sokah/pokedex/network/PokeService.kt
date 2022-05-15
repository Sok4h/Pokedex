package com.sokah.pokedex.network

import android.util.Log
import com.sokah.pokedex.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class PokeService {

    val retrofit = RetrofitHelper.getRetrofit()

    suspend fun findPokemon(pokemon: String):Pokemon? {
        Log.e("TAG", "findPokemon: ",)

            return withContext(Dispatchers.IO) {
                val response = retrofit.create(PokeApi::class.java).searchPokemon(pokemon)

                if(response.isSuccessful&&response.body()!=null){
                        response.body()

                }else{
                    null
                }


        }


    }
}
