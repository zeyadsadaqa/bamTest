package com.zeyadsadaka.bamtest.database

import com.zeyadsadaka.bamtest.repositories.dto.Pokemon
import com.zeyadsadaka.bamtest.repositories.dto.PokemonDetails
import com.zeyadsadaka.bamtest.repositories.dto.PokemonList
import com.zeyadsadaka.bamtest.repositories.dto.Sprites

class PokemonEntityAdapter {
    fun toPokemonEntityList(pokemonList: List<Pokemon>?) = pokemonList?.map {
        PokemonEntity(name = it.name, sprites = null, abilities = null)
    } ?: emptyList()

    fun toPokemonList(list: List<PokemonEntity>): PokemonList {
        val pokemonList = list.map { pokemonEntity ->
            Pokemon(name = pokemonEntity.name)
        }
        return PokemonList(pokemonList.size.toLong(), pokemonList)
    }

    fun toPokemonEntity(pokemonDetails: PokemonDetails) = PokemonEntity(
        name = pokemonDetails.name,
        sprites = pokemonDetails.sprites.frontDefault,
        abilities = pokemonDetails.abilities
    )

    fun toPokemonDetails(pokemonEntity: PokemonEntity) = PokemonDetails(
        name = pokemonEntity.name,
        abilities = pokemonEntity.abilities.orEmpty(),
        sprites = Sprites(pokemonEntity.sprites.orEmpty())
    )
}