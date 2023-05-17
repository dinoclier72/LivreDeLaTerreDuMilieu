package com.tpgate.livredesmercenaires

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpgate.livredesmercenaires.adapters.RecyclerCharacterAdapter
import com.tpgate.livredesmercenaires.model.CharacterResponse
import com.tpgate.livredesmercenaires.model.MediaWikiTextResponse
import com.tpgate.livredesmercenaires.services.*
import java.io.BufferedWriter
import java.io.File
import java.io.FileWriter
import java.util.regex.Pattern

class InfoCharacter : Fragment() {
    companion object {
        const val TAG = "InfoCharacter"
    }
    private val theOneAPIService : TheOneAPIService = TheOneAPIService()
    private val mediaWikiService : MediaWikiService = MediaWikiService()
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_info_character, container, false)
        val characterName = requireArguments().getString("characterName")
        theOneAPIService.characterByName(characterName!!,object: CharacterCallback(){
            override fun fireOnResponseOk(data: CharacterResponse) {
                activity?.runOnUiThread() {
                    val adapter = RecyclerCharacterAdapter(data.docs, navController)
                    val recyclerView1: RecyclerView = view.findViewById(R.id.recycler_view1)
                    recyclerView1.layoutManager = LinearLayoutManager(activity)
                    recyclerView1.adapter = adapter
                }
            }
        })
        mediaWikiService.fetchCharacterWikiText(characterName!!,object: WikiTextCallback(){
            override fun fireOnResponseOk(data: MediaWikiTextResponse) {
                val pageMap = data.query.pages
                val keys = pageMap.keys
                val key = keys.toTypedArray()[0]
                Log.d(TAG,"clee de la map {$key}")
                val page = pageMap[key]
                val wikiText = page!!.revisions[0].slots.main.content
                Log.d(TAG,"original wikitext {$wikiText}")

                val input = "== Biography ==\\n\\nAdanel ended up marrying [[Belemir]] of the [[House of Bëor]]; he was a great-grandson of [[Bëor|Bëor the Old]]. Adanel was the mother of five children, and her fifth and last child was a son: [[Beren (Belemir's son)|Beren]].<ref>The [[History of Middle-earth|'History of Middle-earth']], Vol. XI: [[The War of the Jewels|'The War of the Jewels']], Part Two, The Later Quenta Silmarillion (Chapter 14): \\\"Of the Coming of Men into the West\\\", Commentary, (ii)'The House of Hador'.</ref> His daughter, [[Emeldir]] '\\\"the Man-hearted\\\"', named her son after her father; this child was the renowned [[Beren|Beren Erchamion]] (''Beren the One-handed''). Thus, Adanel was the great-grandmother of Beren son of Emeldir, her granddaughter.\\n\\nThe Wise of the people of Marach were the only [[Men]] to preserve the tale of their original sin, when, soon after their awakening, the Men chose to worship [[Melkor]] instead of [[Eru Ilúvatar|Eru]]. Adanel told this tale (called the 'Tale of Adanel' in [[Morgoth's Ring|'Morgoth's Ring']]) to [[Andreth]] of the House of Bëor.\\n\\nAndreth was a very distant niece of Adanel's husband Belemir through the line of [[Baran]], the eldest son of Bëor, and Belemir through the line of his grandfather [[Belen]], who was the youngest son of Bëor. In addition, Andreth's nephew [[Barahir]] married Adanel's granddaughter Emeldir.<ref>The [[History of Middle-earth|'History of Middle-earth']], Vol. XI: [[The War of the Jewels|'The War of the Jewels']], Part Two: The Later Quenta Silmarillion, XIV: \\\"Of the Coming of Men into the West\\\", (i)'The House of Bëor', pg. 231.</ref>\\n\\n== Etymology =="

                Log.d(TAG,"made up input {$input}")

                val regex = "==\\s?Biography\\s?==([\\S\\s]*?)=="
                val pattern = Pattern.compile(regex)
                val matcher = pattern.matcher(wikiText)


                if (matcher.find()) {
                    val biography = matcher.group(1)
                    Log.d(TAG,biography)
                    val textView : TextView = view.findViewById(R.id.textView)
                    activity?.runOnUiThread(){
                        textView.text = biography
                    }
                } else {
                    Log.d(TAG,"Biography not found")
                }
            }

        })
        return view
    }
}