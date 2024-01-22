package com.zeyadsadaka.bamtest.repositories.dto

data class CategoryResponse(
    val count: Long,
    val categories: List<String>,
)