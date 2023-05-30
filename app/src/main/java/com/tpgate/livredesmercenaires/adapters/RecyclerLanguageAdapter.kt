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
import com.tpgate.livredesmercenaires.model.CharacterData
import com.tpgate.livredesmercenaires.model.Image
import com.tpgate.livredesmercenaires.model.MediaWikiImageResponse
import com.tpgate.livredesmercenaires.services.ImageCallback
import com.tpgate.livredesmercenaires.services.MediaWikiService

class RecyclerLanguageAdapter (private var languageList : List<LanguageController>): RecyclerView.Adapter<RecyclerLanguageAdapter.LanguageViewHolder>()
{
    companion object{
        private const val TAG = "RecyclerLanguageAdapter"
    }
    init{
        Log.d(TAG,"ReyclerCharacterAdapter list: ${languageList.size}")
    }

    inner class LanguageViewHolder(val binding: LanguageRecycleViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = languageList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        Log.d(TAG,"onCreateViewHolder")

        val binding = LanguageRecycleViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LanguageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerLanguageAdapter.LanguageViewHolder, position: Int) {
        Log.d(TAG,"onBindingViewHolder position=${position}")

        val languageData = languageList[position]

        val buttonSelect = holder.binding.choseButton
        if(languageData.language == MainActivity.config.currentLanguage){
            buttonSelect.setBackgroundColor(Color.parseColor("#00FF00"))
            buttonSelect.text = "choisi"
        }else{
            buttonSelect.setBackgroundColor(Color.parseColor("#00FFFF"))
            buttonSelect.text = "choisir"
        }
        buttonSelect.setOnClickListener{
            MainActivity.config.currentLanguage = languageData.language
            notifyDataSetChanged()
        }
        Glide.with(MainActivity.context)
            .load(languageData.img)
            .into(holder.binding.languageImage)
        holder.binding.languageTitle.text = "banana"
        holder.binding.interactionButton.setOnClickListener{
            MainActivity.config.currentLanguage = TranslateLanguage.ENGLISH
            notifyDataSetChanged()
        }
    }
}