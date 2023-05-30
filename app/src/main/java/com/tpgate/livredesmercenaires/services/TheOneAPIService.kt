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

    //list tous le personnages
    fun listAllCharacters(callback: Callback){
        val request = Request.Builder()
            .url("https://the-one-api.dev/v2/character")
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d(TAG,"requete a TheOneAPI characters")
        client.newCall(request).enqueue(callback)
    }

    //choix d'un personnage
    fun characterByName(name:String,callback: Callback){
        val request = Request.Builder()
            .url("https://the-one-api.dev/v2/character?name=$name")
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d(TAG,"requete a TheOneAPI characters specific")
        client.newCall(request).enqueue(callback)
    }

    fun characterById(id:String,callback: Callback){
        val request = Request.Builder()
            .url("https://the-one-api.dev/v2/character/$id")
            .addHeader("Authorization", "Bearer $token")
            .build()
        Log.d(TAG,"requete a TheOneAPI characters specific")
        client.newCall(request).enqueue(callback)
    }

    fun getAllQuotes(callback: Callback){
        val request = Request.Builder()
            .url("https://the-one-api.dev/v2/quote")
            .addHeader("Authorization","Bearer $token")
            .build()
        client.newCall(request).enqueue(callback)
    }

    fun getMovieById(id: String,callback: Callback){
        val request = Request.Builder()
            .url("https://the-one-api.dev/v2/movie/$id")
            .addHeader("Authorization","Bearer $token")
            .build()
        client.newCall(request).enqueue(callback)
    }
}