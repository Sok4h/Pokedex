package com.sokah.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout
import com.sokah.pokedex.network.PokeService
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var coroutineJob: Job? = null
    val api = PokeService()
    lateinit var  input :TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        input = findViewById(R.id.inputPokemon)
        input.setEndIconOnClickListener {

            Toast.makeText(this,"Buscando pokemon",Toast.LENGTH_SHORT).show()

            if(input.editText?.text!!.isNotEmpty()){
                searchPokemon()

            }

        }

    }

    private fun searchPokemon() {
        coroutineJob= CoroutineScope(Dispatchers.IO).launch{

            api.findPokemon(input.editText?.text.toString())
        }
    }
}