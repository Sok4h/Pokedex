package com.sokah.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.google.gson.Gson
import com.sokah.pokedex.databinding.ActivityMainBinding
import com.sokah.pokedex.model.Pokemon
import com.sokah.pokedex.network.PokeService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var coroutineJob: Job? = null
    val api = PokeService()
    var pokemon: Pokemon?=null
    val binding : ActivityMainBinding by lazy {

        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.inputPokemon.setEndIconOnClickListener {

                searchPokemon()

            }
        binding.btnSearchHome.setOnClickListener {

            searchPokemon()
        }
        }





    private fun searchPokemon() {

        if (binding.inputPokemon.editText?.text!!.isNotEmpty()) {
            coroutineJob = CoroutineScope(Dispatchers.IO).launch {

                 pokemon=  api.findPokemon(binding.inputPokemon.editText?.text.toString())

                val intent = Intent(applicationContext,PokemonActivity::class.java)
                var gson = Gson()

                intent.putExtra("pokemon",gson.toJson(pokemon))
                startActivity(intent)
            }
        } else{

            Toast.makeText(this,"Ponga un nombre",Toast.LENGTH_SHORT).show()
        }
    }


}