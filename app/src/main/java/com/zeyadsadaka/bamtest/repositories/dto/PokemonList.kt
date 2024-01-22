package com.zeyadsadaka.bamtest.repositories.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonList(
    val count: Long,
    val results: List<Pokemon>,
)