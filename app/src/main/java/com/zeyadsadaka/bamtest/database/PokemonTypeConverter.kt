package com.zeyadsadaka.bamtest.database

import androidx.room.TypeConverter
import com.zeyadsadaka.bamtest.repositories.dto.Ability
import com.zeyadsadaka.bamtest.repositories.dto.AbilityContainer

class PokemonTypeConverter {
    @TypeConverter
    fun toAbilityContainer(stringAbilities: String): List<AbilityContainer> {
        val splitAbilities = stringAbilities.split(",")
        val list = splitAbilities.map {
            AbilityContainer(ability = Ability(it))
        }
        return list
    }

    @TypeConverter
    fun toAbilityString(list: List<AbilityContainer>?): String {
        val stringListBuilder = StringBuilder()
        list?.let {
            for (abilityContainer in it) {
                stringListBuilder.append(abilityContainer.ability).append(",")
            }
        }
        return stringListBuilder.toString()
    }
}