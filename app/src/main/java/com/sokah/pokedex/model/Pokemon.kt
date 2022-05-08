package com.sokah.pokedex.model

import com.google.gson.annotations.SerializedName

data class Pokemon(

    val name: String,
    val id: Int,
    val height: Int,
    val weight: Int,
    @SerializedName("stats")
    val stats: List<Stat>,
    val sprites:Sprites

    )

data class Sprites (

    val other :Other
        )

data class Other(
    @SerializedName("official-artwork")
    val artwork:Artwork
)

data class Artwork(
    val front_default:String
)

data class Stat(
    val base_stat: Int,
    val stat: StatX
)

data class StatX(
    @SerializedName("name")
    val name: String,
    )