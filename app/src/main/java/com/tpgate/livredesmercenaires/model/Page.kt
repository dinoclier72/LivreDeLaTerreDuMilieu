package com.tpgate.livredesmercenaires.model

data class Page(
    val images: List<Image>,
    val ns: Int,
    val pageid: Int,
    val title: String,
)