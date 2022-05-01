package com.sokah.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.sokah.pokedex.databinding.ActivityMainBinding
import com.sokah.pokedex.network.PokeService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var coroutineJob: Job? = null
    val api = PokeService()
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

                api.findPokemon(binding.inputPokemon.editText?.text.toString())
            }
        }else{

            Toast.makeText(this,"Ponga un nombre",Toast.LENGTH_SHORT).show()
        }
    }


}