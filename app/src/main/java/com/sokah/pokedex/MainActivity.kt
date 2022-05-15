package com.sokah.pokedex

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.sokah.pokedex.databinding.ActivityMainBinding
import com.sokah.pokedex.model.Pokemon
import com.sokah.pokedex.network.PokeService
import com.sokah.pokedex.view.PokemonAdapter
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var coroutineJob: Job? = null
    private val api = PokeService()

    private var pokemon: Pokemon? = null
    private var pokemonAdapter = PokemonAdapter()

    private var myPokemonsRefs = Firebase.firestore
        .collection("users")
        .document("test")
        .collection("pokemons")

    private val binding: ActivityMainBinding by lazy {
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

        binding.rvMyPokemonsHome.apply {
            layoutManager = GridLayoutManager(applicationContext, 2)
            adapter = pokemonAdapter
            //layoutManager = LinearLayoutManager(applicationContext)
        }

        listenPokemons()

    }

    private fun listenPokemons(){
        myPokemonsRefs.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Toast.makeText(this, "No se pudo cargar la información de tus pokemons", Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }

            if (snapshot !== null && !snapshot.isEmpty) {
                pokemonAdapter.clear()
                snapshot.forEach {
                    Log.e(">>>", "Current data: ${it.data}")
                    val newPokemon = it.toObject(Pokemon::class.java)
                    newPokemon.uid = it.id

                    pokemonAdapter.addPokemon(newPokemon)
                }

            } else {
                Toast.makeText(this, "Aun no tienes pokemons, atrapa tu primer pokemon!", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun searchPokemon() {
        if (binding.inputPokemon.editText?.text!!.isNotEmpty()) {
            coroutineJob = CoroutineScope(Dispatchers.IO).launch {

                pokemon = api.findPokemon(binding.inputPokemon.editText?.text.toString())

                if (pokemon == null) {

                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            applicationContext,
                            "El pokemon buscado no existe",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                } else {

                    val intent = Intent(applicationContext, PokemonActivity::class.java)
                    val gson = Gson()

                    intent.putExtra("pokemon", gson.toJson(pokemon))
                    startActivity(intent)
                }

            }
        } else {

            Toast.makeText(this, "Ponga un nombre", Toast.LENGTH_SHORT).show()
        }
    }
}