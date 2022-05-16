package com.sokah.pokedex

import android.content.Intent
import android.graphics.drawable.Drawable
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
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.*


class PokemonActivity : AppCompatActivity() {

    private val binding: ActivityPokemonBinding by lazy {
        ActivityPokemonBinding.inflate(layoutInflater)
    }

    lateinit var pokemon: Pokemon

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val isMy = intent.extras?.getBoolean("isMy")

        val gson = Gson()
        pokemon = gson.fromJson(intent.extras?.getString("pokemon"), Pokemon::class.java)
        pokemon.date = Calendar.getInstance().time

        /*Log.e("TAG", pokemon.types.toString() )

        val drawableId = resources.getIdentifier("ic_${tipo}", "drawable", packageName)*/

        //binding.imgPokemon.setBackgroundResource(drawableId)
        Glide.with(applicationContext)
            .load(pokemon.sprites.other.artwork.front_default)
            .into(binding.imgPokemon)
        binding.tvPokeName.text = pokemon.name
        setupPokemon()

        if (isMy!!) {
            binding.button.text = "Liberar pokémon"
        }

        binding.button.setOnClickListener {
            if (isMy!!) {
                releasePokemon()
            } else {
                uploadPokemon()
            }
        }
    }

    private fun uploadPokemon() {
        lifecycleScope.launch(Dispatchers.IO) {
            Firebase.firestore
                .collection("users")
                .document("test")
                .collection("pokemons")
                .add(pokemon)
                .await()

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Se atrapó el pokémon", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
            }

        }
    }

    private fun releasePokemon() {
        lifecycleScope.launch(Dispatchers.IO) {
            val uid = intent.extras?.getString("uid")

            Firebase.firestore
                .collection("users")
                .document("test")
                .collection("pokemons")
                .document(uid!!)
                .delete()
                .await()

            withContext(Dispatchers.Main) {
                Toast.makeText(applicationContext, "Pokemon liberado con exito", Toast.LENGTH_SHORT)
                    .show()

                val intent = Intent(applicationContext, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                applicationContext.startActivity(intent)
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