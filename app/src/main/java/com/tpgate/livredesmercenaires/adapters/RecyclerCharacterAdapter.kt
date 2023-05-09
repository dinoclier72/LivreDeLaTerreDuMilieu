package com.tpgate.livredesmercenaires.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tpgate.livredesmercenaires.databinding.CharacterrecyclerviewBinding
import com.tpgate.livredesmercenaires.model.CharacterData

class RecyclerCharacterAdapter (private var characterDatas : List<CharacterData>): RecyclerView.Adapter<RecyclerCharacterAdapter.CharacterViewHolder>()
{
    companion object{
        private const val TAG = "RecyclerCharacterAdapter"
    }
    init{
        Log.d(TAG,"ReyclerCharacterAdapter list: ${characterDatas.size}")
    }

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
    }
}