package com.tpgate.livredesmercenaires

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.mlkit.nl.translate.TranslateLanguage
import com.tpgate.livredesmercenaires.adapters.RecyclerLanguageAdapter
import com.tpgate.livredesmercenaires.appController.LanguageController
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class LanguageConfig : Fragment() {
    companion object {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val languageList = mutableListOf(
            LanguageController(TranslateLanguage.FRENCH,"french",R.drawable.fr)
        )

        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_language_config, container, false)

        val quitButton : Button = view.findViewById(R.id.quitButton)
        quitButton.setOnClickListener{
            val file = File(requireContext().filesDir, "config.json")
            if (!file.exists()) {
                file.createNewFile()
            }
            val outputStream = ObjectOutputStream(FileOutputStream(file))
            outputStream.writeObject(MainActivity.config)
            outputStream.close()
            val manager = fragmentManager
            manager?.popBackStack()
        }

        val recyclerView : RecyclerView = view.findViewById(R.id.language_recycler)
        val adapter = RecyclerLanguageAdapter(languageList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        return view
    }
}