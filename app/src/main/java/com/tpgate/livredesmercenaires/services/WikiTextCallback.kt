package com.tpgate.livredesmercenaires.services

import android.util.Log
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class WikiTextCallback:Callback {
    companion object{
        const val TAG = "WikiTextCallback"
        val gson = Gson()
    }

    //abstract fun fireOnResponseOk(data: MediaWikiTextResponse)

    override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
    }

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json = response.body!!.string()
            Log.d(CharacterCallback.TAG,"querry response :$json")
            //val data : MediaWikiTextResponse = CharacterCallback.gson.fromJson(json, MediaWikiTextResponse::class.java )
            //fireOnResponseOk(data)
        }
    }
}