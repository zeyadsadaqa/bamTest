package com.zeyadsadaka.bamtest.repositories.dto

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ability(
    val name: String,
)
