package com.tpgate.livredesmercenaires.model

data class LotrCharacters(
    val docs: List<Doc>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)