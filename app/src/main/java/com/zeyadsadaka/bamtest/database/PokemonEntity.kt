package com.zeyadsadaka.bamtest.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.zeyadsadaka.bamtest.repositories.dto.AbilityContainer

@Entity(tableName = "pokemon_table")
@TypeConverters(PokemonTypeConverter::class)
data class PokemonEntity(
    @ColumnInfo(name = "pokemon_name")
    @PrimaryKey
    val name: String,

    @ColumnInfo("sprites")
    val sprites: String?,

    @ColumnInfo("abilities")
    val abilities: List<AbilityContainer>?,

    )

