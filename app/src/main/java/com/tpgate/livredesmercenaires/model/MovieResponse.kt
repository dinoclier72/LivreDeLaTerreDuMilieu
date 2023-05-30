package com.tpgate.livredesmercenaires.model

data class MovieResponse(
    val docs: List<MovieData>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)