package com.tpgate.livredesmercenaires.services

import android.util.Log
import com.google.gson.Gson
import com.tpgate.livredesmercenaires.model.CharacterResponse
import com.tpgate.livredesmercenaires.model.MediaWikiImageResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

abstract class ImageCallback :Callback {
    companion object{
        const val TAG = "ImageCallback"
        val gson = Gson()
    }

    abstract fun fireOnResponseOk(data: MediaWikiImageResponse)

    override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
    }

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json = response.body!!.string()
            Log.d(CharacterCallback.TAG,"querry response :$json")
            val data : MediaWikiImageResponse = CharacterCallback.gson.fromJson(json, MediaWikiImageResponse::class.java )
            fireOnResponseOk(data)
        }
    }
}