package com.zeyadsadaka.bamtest.repositories.dto

data class PokemonList(
    val count: Long,
    val results: List<Pokemon>,
)