package com.tpgate.livredesmercenaires.appController

import com.google.mlkit.nl.translate.TranslateLanguage

data class AppConfig(
    var currentLanguage : String = TranslateLanguage.FRENCH
)
