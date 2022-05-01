package com.sokah.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input = findViewById<TextInputLayout>(R.id.inputPokemon)

        input.setEndIconOnClickListener {

            Toast.makeText(this,"Buscando pokemon",Toast.LENGTH_SHORT).show()
        }
    }
}