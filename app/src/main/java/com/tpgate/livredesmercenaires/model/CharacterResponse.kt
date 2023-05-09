package com.tpgate.livredesmercenaires.model

data class CharacterResponse(
    val docs: List<CharacterData>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)