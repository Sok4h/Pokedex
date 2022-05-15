package com.sokah.pokedex.model

import android.annotation.SuppressLint
import com.google.gson.annotations.SerializedName
import java.time.Month
import java.time.format.TextStyle
import java.util.*

data class Pokemon(

    val name: String = "",
    val id: Int = -1,
    val height: Int = -1,
    val weight: Int = -1,
    @SerializedName("stats")
    val stats: List<Stat> = emptyList(),
    val sprites: Sprites = Sprites(Other(Artwork(""))),
    var date: Date? = null,
    var uid: String? = null
)

data class Sprites(

    val other: Other = Other(Artwork(""))
)

data class Other(
    @SerializedName("official-artwork")
    val artwork: Artwork = Artwork("")
)

data class Artwork(
    val front_default: String = ""
)

data class Stat(
    val base_stat: Int = 0,
    val stat: StatX = StatX( "")
)

data class StatX(
    @SerializedName("name")
    val name: String = "",
)

@SuppressLint("NewApi")
fun getDateParse(pokemon: Pokemon): String {
    val cal = Calendar.getInstance()
    cal.time = pokemon.date

    val day = cal.get(Calendar.DAY_OF_MONTH).toString()
    val month = Month.of(cal.get(Calendar.MONTH)+1)
    val monthParse = month.getDisplayName(TextStyle.SHORT, Locale.getDefault())
    val year = cal.get(Calendar.YEAR).toString()

    val hour = cal.get(Calendar.HOUR_OF_DAY).toString()
    val minutes = cal.get(Calendar.MINUTE).toString()

    return "$day, $monthParse $year - $hour:$minutes"
}