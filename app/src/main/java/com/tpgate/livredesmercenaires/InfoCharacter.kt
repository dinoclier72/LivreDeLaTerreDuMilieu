package com.tpgate.livredesmercenaires

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.translation.Translator
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.tpgate.livredesmercenaires.adapters.RecyclerCharacterAdapter
import com.tpgate.livredesmercenaires.model.CharacterResponse
import com.tpgate.livredesmercenaires.model.MediaWikiTextResponse
import com.tpgate.livredesmercenaires.services.*
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

                val biography: String = extractBiography(wikiText)

                val textView : TextView = view.findViewById(R.id.textView)

                if(MainActivity.config.currentLanguage != TranslateLanguage.ENGLISH) {
                    translateText(biography, "fr") { translateText ->
                        activity?.runOnUiThread() {
                            textView.text = translateText
                        }
                    }
                }else{
                    activity?.runOnUiThread() {
                        textView.text = biography
                    }
                }
            }

        })
        return view
    }

    fun extractBiography(rawText : String) : String{
        var output : String

        val regex = "==\\s?Biography\\s?==([\\S\\s]*?)[^=]==[^=]"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(rawText)


        if (matcher.find()) {
            output = matcher.group(1)
        } else {
            output = "No biography for this character"
        }

        return output
    }

    fun translateText(text: String, targetLanguage: String, callback: (String) -> Unit) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.FRENCH)
            .build()
        val translator = Translation.getClient(options)
        val conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        translator.downloadModelIfNeeded(conditions)
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translatedText ->
                        callback(translatedText) // Appeler le callback avec le texte traduit
                    }
                    .addOnFailureListener { exception ->
                        // Gérer les échecs de traduction
                    }
            }
            .addOnFailureListener { exception ->
                // Gérer les échecs de téléchargement du modèle
            }
    }

}