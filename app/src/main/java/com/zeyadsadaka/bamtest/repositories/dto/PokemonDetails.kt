package com.zeyadsadaka.bamtest.repositories.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetails(
    val abilities: List<AbilityContainer>,
    val name: String,
    val sprites: Sprites,
)