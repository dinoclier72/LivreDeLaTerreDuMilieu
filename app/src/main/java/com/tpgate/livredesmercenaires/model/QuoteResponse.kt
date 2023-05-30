package com.tpgate.livredesmercenaires.model

data class QuoteResponse(
    val docs: List<QuoteData>,
    val limit: Int,
    val offset: Int,
    val page: Int,
    val pages: Int,
    val total: Int
)