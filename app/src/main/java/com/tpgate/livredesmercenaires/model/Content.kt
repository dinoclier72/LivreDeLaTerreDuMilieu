package com.tpgate.livredesmercenaires.model

import com.google.gson.annotations.SerializedName

data class Content(
    val contentmodel: String,
    val contentformat: String,
    @SerializedName("*")
    val content: String
)
