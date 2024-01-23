package com.zeyadsadaka.bamtest.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [PokemonEntity::class],
    version = 1,
    exportSchema = false,
)
@TypeConverters(PokemonTypeConverter::class)
abstract class PokemonDB : RoomDatabase() {
    abstract fun pokemonDao(): PokemonDao

    companion object {
        @Volatile
        private var instance: PokemonDB? = null

        fun getInstance(context: Context): PokemonDB {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): PokemonDB {
            return Room.databaseBuilder(
                context,
                PokemonDB::class.java,
                "pokemon-db"
            ).build()
        }
    }
}