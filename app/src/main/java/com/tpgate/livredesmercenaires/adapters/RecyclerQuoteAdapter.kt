package com.tpgate.livredesmercenaires.adapters

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.mlkit.nl.translate.TranslateLanguage
import com.tpgate.livredesmercenaires.LanguageConfig
import com.tpgate.livredesmercenaires.MainActivity
import com.tpgate.livredesmercenaires.R
import com.tpgate.livredesmercenaires.appController.LanguageController
import com.tpgate.livredesmercenaires.databinding.CharacterrecyclerviewBinding
import com.tpgate.livredesmercenaires.databinding.LanguageRecycleViewBinding
import com.tpgate.livredesmercenaires.databinding.QuoteGuesserRecycleViewBinding
import com.tpgate.livredesmercenaires.model.CharacterData
import com.tpgate.livredesmercenaires.model.Image
import com.tpgate.livredesmercenaires.model.MediaWikiImageResponse
import com.tpgate.livredesmercenaires.services.ImageCallback
import com.tpgate.livredesmercenaires.services.MediaWikiService

class RecyclerQuoteAdapter (private var tries: List<String>,private var correctName: String): RecyclerView.Adapter<RecyclerQuoteAdapter.QuoteViewHolder>()
{
    companion object{
        private const val TAG = "RecyclerQuoteAdapter"
    }
    init{
        Log.d(TAG,"ReyclerCharacterAdapter list: ${tries.size}")
    }

    inner class QuoteViewHolder(val binding: QuoteGuesserRecycleViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = tries.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        Log.d(TAG,"onCreateViewHolder")

        val binding = QuoteGuesserRecycleViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerQuoteAdapter.QuoteViewHolder, position: Int) {
        Log.d(TAG,"onBindingViewHolder position=${position}")

        val currentTry = tries[position]
        holder.binding.textViewCharacterName.text = currentTry
        if(currentTry == correctName){
            holder.binding.quoteValid.text = "valide"
        }else{
            holder.binding.quoteValid.text = "pas valide"
        }
    }

    fun updateDataSet(newList: List<String>){
        tries = newList
        notifyDataSetChanged()
        Log.d(TAG, tries.size.toString())
    }
}