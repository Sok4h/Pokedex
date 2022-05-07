package com.sokah.pokedex

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.utils.ColorTemplate
import com.sokah.pokedex.databinding.ActivityMainBinding
import com.sokah.pokedex.databinding.ActivityPokemonBinding
import com.sokah.pokedex.model.Pokemon
import com.sokah.pokedex.model.Stat
import com.sokah.pokedex.model.StatX
import java.util.ArrayList

class PokemonActivity : AppCompatActivity() {

    val binding: ActivityPokemonBinding by lazy {

        ActivityPokemonBinding.inflate(layoutInflater)
    }

    val pokemon = Pokemon(
        "Pikachu", 32, 1, 2, listOf(
            Stat(30,  StatX("fuerza")), Stat(60,  StatX("hp")), Stat(10, StatX("velocidad"))
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupPokemon()
    }

    private fun setupPokemon() {


        val entries: ArrayList<BarEntry> = ArrayList()
        val label: ArrayList<String> = ArrayList()

        for ((index, stat) in pokemon.stats.withIndex()) {

            entries.add(BarEntry(index.toFloat(), stat.baseStat.toFloat()))
            label.add(stat.stat.name)
        }

        initBar()
        val barDataSet = BarDataSet(entries, "label")
        barDataSet.setColors(*ColorTemplate.COLORFUL_COLORS)

        val data = BarData(barDataSet)

        binding.barChart.data = data
        binding.barChart.animateY(1000)
        binding.barChart.invalidate()



    }

    fun initBar(){


//        hide grid lines
        binding.barChart.axisLeft.setDrawGridLines(false)
        val xAxis: XAxis = binding.barChart.xAxis
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
        binding.barChart.animateY(3000)

        // to draw label on xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        xAxis.valueFormatter = MyAxisFormatter()
        xAxis.setDrawLabels(true)
        xAxis.granularity = 1f
        xAxis.labelRotationAngle = +90f

    }

    inner class MyAxisFormatter : IndexAxisValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt()
            Log.d("TAG", "getAxisLabel: index $index")
            return if (index < pokemon.stats.size) {
                pokemon.stats[index].stat.name
            } else {
                ""
            }
        }
    }


}