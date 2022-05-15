package com.sokah.pokedex

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.sokah.pokedex.databinding.ActivityPokemonBinding
import com.sokah.pokedex.model.Pokemon
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


class PokemonActivity : AppCompatActivity() {

    private val binding: ActivityPokemonBinding by lazy {

        ActivityPokemonBinding.inflate(layoutInflater)
    }

    private var myPokemonsRefs = Firebase.firestore
        .collection("users")
        .document("test")
        .collection("pokemons")


    lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        var gson = Gson()
        pokemon = gson.fromJson(intent.extras?.getString("pokemon"), Pokemon::class.java)
        pokemon.date = Calendar.getInstance().time
        Glide.with(applicationContext)
            .load(pokemon.sprites.other.artwork.front_default)
            .into(binding.imgPokemon)
        binding.tvPokeName.text = pokemon.name
        setupPokemon()

        binding.button.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                myPokemonsRefs.add(pokemon).addOnCompleteListener {

                    Toast.makeText(applicationContext, "Se atrapo el pokémon", Toast.LENGTH_SHORT)
                        .show()

                }
            }
        }
    }

    private fun setupPokemon() {

        val entries: ArrayList<BarEntry> = ArrayList()
        val label: ArrayList<String> = ArrayList()

        for ((index, stat) in pokemon.stats.withIndex()) {
            entries.add(BarEntry(index.toFloat(), stat.base_stat.toFloat()))
            label.add(stat.stat.name)
        }

        initBar()

        val barDataSet = BarDataSet(entries, "label").apply {
            setColors(*ColorTemplate.COLORFUL_COLORS)
            valueTextSize = 12f
            valueTextColor = ContextCompat.getColor(applicationContext, R.color.white)
            valueTypeface = ResourcesCompat.getFont(applicationContext, R.font.poppins)
        }

        val barData = BarData(barDataSet).apply {
            barWidth = 0.7f
        }

        binding.barChart.apply {
            data = barData
            setDrawValueAboveBar(false)
            invalidate()
        }

    }

    private fun initBar() {

        val xAxis: XAxis = binding.barChart.xAxis
        binding.barChart.axisLeft.axisMinimum = 0f

        //hide grid lines
        binding.barChart.axisLeft.setDrawGridLines(false)
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)


        //remove right y-axis
        binding.barChart.axisRight.isEnabled = false
        binding.barChart.axisLeft.isEnabled = false

        //remove legend
        binding.barChart.legend.isEnabled = false


        //remove description label
        binding.barChart.description.isEnabled = false


        //add animation
        binding.barChart.animateY(1000)

        // to draw label on xAxis
        xAxis.apply {
            typeface = ResourcesCompat.getFont(applicationContext, R.font.poppins)
            textSize = 12f

            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter = MyAxisFormatter()
            setDrawLabels(true)
            granularity = 1f
        }

    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < pokemon.stats.size) {
                pokemon.stats[index].stat.name + ":"
            } else {
                ""
            }
        }
    }


}