package com.tpgate.livredesmercenaires.adapters

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tpgate.livredesmercenaires.MainActivity
import com.tpgate.livredesmercenaires.R
import com.tpgate.livredesmercenaires.databinding.CharacterrecyclerviewBinding
import com.tpgate.livredesmercenaires.model.CharacterData
import com.tpgate.livredesmercenaires.model.Image
import com.tpgate.livredesmercenaires.model.MediaWikiImageResponse
import com.tpgate.livredesmercenaires.services.ImageCallback
import com.tpgate.livredesmercenaires.services.MediaWikiService

class RecyclerCharacterAdapter (private var characterDatas : List<CharacterData>,private val navController: NavController): RecyclerView.Adapter<RecyclerCharacterAdapter.CharacterViewHolder>()
{
    companion object{
        private const val TAG = "RecyclerCharacterAdapter"
    }
    init{
        Log.d(TAG,"ReyclerCharacterAdapter list: ${characterDatas.size}")
    }
    private val mediaWikiService: MediaWikiService = MediaWikiService()

    inner class CharacterViewHolder(val binding: CharacterrecyclerviewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun getItemCount(): Int = characterDatas.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        Log.d(TAG,"onCreateViewHolder")

        val binding = CharacterrecyclerviewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CharacterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerCharacterAdapter.CharacterViewHolder, position: Int) {
        Log.d(TAG,"onBindingViewHolder position=${position}")

        val characterData = characterDatas[position]
        holder.binding.textViewCharacterBirth.text = characterData.birth
        holder.binding.textViewCharacterHair.text = characterData.hair
        holder.binding.textViewCharacterDeath.text = characterData.death
        holder.binding.textViewCharacterHeight.text = characterData.height
        holder.binding.textViewCharacterName.text = characterData.name
        holder.binding.textViewCharacterGender.text = characterData.gender
        holder.binding.textViewCharacterRace.text = characterData.race
        holder.binding.textViewCharacterSpouce.text = characterData.spouse

        val pageTitle = characterData.wikiUrl.substringAfterLast("/")
        mediaWikiService.fetchImages(pageTitle,object:ImageCallback(){
            override fun fireOnResponseOk(data: MediaWikiImageResponse) {
                var imageUrl = ""
                val pageMap = data.query.pages
                val keys = pageMap.keys
                val key = keys.toTypedArray()[0]
                Log.d(TAG,"clee de la map {$key}")
                val page = pageMap[key]
                val images = page?.images
                if(images != null){
                    Log.d(TAG,"imageExist")
                    val image: Image = images[0]
                    val fileName = image.title
                    val cleanFilename = fileName.removePrefix("File:")
                    val usableFileName = cleanFilename.replace(" ","_")
                    imageUrl = "https://lotr.fandom.com/wiki/Special:FilePath/$usableFileName"
                }else{
                    Log.d(TAG,"noImage")
                    imageUrl = "https://placekitten.com/256/256"
                }
                Log.d(TAG,"final imageURL:${imageUrl}")
                MainActivity.handler.post {
                    Glide.with(MainActivity.context)
                        .asBitmap()
                        .load(imageUrl)
                        .into(holder.binding.imageViewCharacter)
                }
            }
        })
        holder.binding.imageViewCharacter.setOnClickListener {
            Log.d(TAG,"depart vers le prochain fragment")
            val bundle = bundleOf("characterName" to characterData.name)
            navController.navigate(R.id.infoCharacter,bundle)
        }
    }
}