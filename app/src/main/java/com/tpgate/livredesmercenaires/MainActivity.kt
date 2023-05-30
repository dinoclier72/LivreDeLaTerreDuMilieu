package com.tpgate.livredesmercenaires

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.google.mlkit.nl.translate.TranslateLanguage
import com.tpgate.livredesmercenaires.appController.AppConfig
import com.tpgate.livredesmercenaires.databinding.ActivityMainBinding
import com.tpgate.livredesmercenaires.model.CharacterResponse
import java.io.File
import java.io.FileInputStream
import java.io.ObjectInputStream

class MainActivity : AppCompatActivity() {
    companion object{
        lateinit var context : Context
        val handler = Handler(Looper.getMainLooper())
        lateinit var config : AppConfig
        lateinit var characterList : CharacterResponse
    }

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        context = this
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)



        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController: NavController = findNavController(R.id.nav_host_fragment_content_main)

        val file = File(filesDir, "config.json")
        if(!file.isFile){
            config = AppConfig(TranslateLanguage.ENGLISH)
        }else{
            val objectInputStream : ObjectInputStream = ObjectInputStream(FileInputStream(file))
            config = objectInputStream.readObject() as AppConfig
            objectInputStream.close()
        }

        val characterButton: ImageButton = findViewById(R.id.characterButton)
        val languageButton: ImageButton = findViewById(R.id.languageButton)
        val mainMenuButton: ImageButton = findViewById(R.id.mainMenuButton)
        val characterGuesserButton: ImageButton = findViewById(R.id.guessCharacterButton)
        val quoteGuesserButton: ImageButton = findViewById(R.id.guessQuoteButton)

        characterButton.setOnClickListener {
            navController.navigate(R.id.characterFragment)
        }

        languageButton.setOnClickListener {
            navController.navigate(R.id.languageConfig)
        }

        mainMenuButton.setOnClickListener {
            navController.navigate(R.id.FirstFragment)
        }

        characterGuesserButton.setOnClickListener {
            navController.navigate(R.id.quoteGuesser)
        }

        quoteGuesserButton.setOnClickListener {
            navController.navigate(R.id.quoteGuesser)
        }
    }
}