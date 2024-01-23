package com.zeyadsadaka.bamtest.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllPokemons(pokemonList: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemonEntity: PokemonEntity)

    @Query("SELECT * FROM pokemon_table")
    suspend fun getAllPokemons(): List<PokemonEntity>

    @Query("SELECT * FROM pokemon_table WHERE pokemon_name =:pokemonName")
    suspend fun getPokemon(pokemonName: String): PokemonEntity
}