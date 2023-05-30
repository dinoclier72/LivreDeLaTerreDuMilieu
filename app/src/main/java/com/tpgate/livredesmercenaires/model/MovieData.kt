package com.tpgate.livredesmercenaires.model

data class MovieData(
    val _id: String,
    val academyAwardNominations: Int,
    val academyAwardWins: Int,
    val boxOfficeRevenueInMillions: Int,
    val budgetInMillions: Int,
    val name: String,
    val rottenTomatoesScore: Int,
    val runtimeInMinutes: Int
)