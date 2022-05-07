package com.sokah.pokedex.model

import com.google.gson.annotations.SerializedName
import com.squareup.moshi.Json

data class Pokemon(

    @Json(name = "Name")
    val name: String,
    val id: Int,
    val height: Int,
    val weight: Int,
    @SerializedName("stats")
    val stats: List<Stat>,

    )

data class Stat(
    val base_stat: Int,
    val stat: StatX
)

data class StatX(
    @SerializedName("name")
    val name: String,
    )