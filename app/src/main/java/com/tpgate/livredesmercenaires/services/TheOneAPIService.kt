package com.tpgate.livredesmercenaires.services

import android.util.Log
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class TheOneAPIService {
    companion object{
        const val TAG = "TheOneAPIService"
        const val token = "[REDACTED]"
        val client = OkHttpClient()
    }
    fun listAllCharacters(callback: Callback){
        val request = Request.Builder()
            .url("https://the-one-api.dev/v2/character")
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d(TAG,"requete a TheOneAPI characters")
        client.newCall(request).enqueue(callback)
    }

    fun characterByName(name:String,callback: Callback){
        val request = Request.Builder()
            .url("https://the-one-api.dev/v2/character?name=$name")
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d(TAG,"requete a TheOneAPI characters specific")
        client.newCall(request).enqueue(callback)
    }
}