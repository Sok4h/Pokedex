package com.sokah.pokedex

import com.squareup.moshi.Json

data class Pokemon (

    @Json(name = "Name")
    val name:String
    )
