package com.example.lab6_pokeapi

data class Pokemon(
    val name: String,
    val imageUrl: String,
    val type: List<String>,
    val id:Int
)
