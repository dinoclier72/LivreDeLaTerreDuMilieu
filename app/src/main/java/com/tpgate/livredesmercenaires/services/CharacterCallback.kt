package com.tpgate.livredesmercenaires.services

import android.util.Log
import com.google.gson.Gson
import com.tpgate.livredesmercenaires.model.CharacterResponse
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

abstract class CharacterCallback: Callback {
    companion object{
        const val TAG = "CharacterCallBack"
        val gson = Gson()
    }
    abstract fun fireOnResponseOk(data:CharacterResponse)

    override fun onFailure(call: Call, e: IOException) {
        e.printStackTrace()
    }

    override fun onResponse(call: Call, response: Response) {
        if (!response.isSuccessful) {
            throw IOException("Unexpected code $response")
        } else {
            val json = response.body!!.string()
            Log.d(TAG,"querry response :$json")
            val data : CharacterResponse = gson.fromJson(json,CharacterResponse::class.java )
            fireOnResponseOk(data)
        }
    }

}