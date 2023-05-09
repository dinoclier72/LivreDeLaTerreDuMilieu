package com.tpgate.livredesmercenaires.services

import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class MediaWikiService {
    companion object{
        const val TAG = "MediaWikiService"
        val client : OkHttpClient = OkHttpClient()
    }

    fun fetchImages(name: String,callback: Callback){
        val request = Request.Builder()
            .url("https://lotr.fandom.com/api.php?action=query&format=json&prop=images&titles=${name}")
            .build()
        client.newCall(request).enqueue(callback)
    }
}