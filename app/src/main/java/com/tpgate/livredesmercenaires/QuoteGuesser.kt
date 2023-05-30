package com.tpgate.livredesmercenaires

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpgate.livredesmercenaires.adapters.RecyclerQuoteAdapter
import com.tpgate.livredesmercenaires.model.*
import com.tpgate.livredesmercenaires.services.CharacterCallback
import com.tpgate.livredesmercenaires.services.MovieCallBack
import com.tpgate.livredesmercenaires.services.QuoteCallBack
import com.tpgate.livredesmercenaires.services.TheOneAPIService
import java.util.Random

class QuoteGuesser : Fragment() {
    companion object {
        const val TAG = "QuoteGuesser"
        val Random = Random()
    }
    var characterNameList: List<String> = emptyList()
    var nametried : MutableList<String> = mutableListOf()

    lateinit var choosenCharacter: String
    lateinit var autoCompleteTextView : AutoCompleteTextView
    lateinit var choosenQuote : QuoteData

    lateinit var adapter: RecyclerQuoteAdapter

    val theOneAPIService : TheOneAPIService = TheOneAPIService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_quote_guesser, container, false)
        autoCompleteTextView = view.findViewById(R.id.autoCompleteTextView)
        autoCompleteTextView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                nametried.add(parent.getItemAtPosition(position).toString())
                adapter.updateDataSet(nametried)
                autoCompleteTextView.setText("")
            }
        val recyclerView : RecyclerView = view.findViewById(R.id.recycler_view_quoteTry)
        theOneAPIService.listAllCharacters(object : CharacterCallback() {
            override fun fireOnResponseOk(data: CharacterResponse) {
                activity?.runOnUiThread() {
                    characterNameList = getCharacterNameList(data.docs)
                    val adapter = ArrayAdapter(
                        MainActivity.context,
                        android.R.layout.simple_dropdown_item_1line,
                        characterNameList
                    )
                    autoCompleteTextView.setAdapter(adapter)
                }
            }
        })
        theOneAPIService.getAllQuotes(object: QuoteCallBack(){
            override fun fireOnResponseOk(data: QuoteResponse) {
                val randomPicked : Int = Random.nextInt(data.limit)
                Log.d(TAG,"$randomPicked")
                val quote: QuoteData = data.docs[randomPicked]
                activity?.runOnUiThread(){
                    val textview : TextView = view.findViewById(R.id.Quote)
                    textview.text = quote.dialog
                }
                theOneAPIService.getMovieById(quote.movie,object: MovieCallBack() {
                    override fun fireOnResponseOk(data: MovieResponse) {
                        val textView : TextView = view.findViewById(R.id.Movie)
                        textView.text = data.docs[0].name
                    }
                })
                theOneAPIService.characterById(quote.character,object: CharacterCallback() {
                    override fun fireOnResponseOk(data: CharacterResponse) {
                        choosenCharacter = data.docs[0].name
                        Log.d(TAG,choosenCharacter)
                        adapter = RecyclerQuoteAdapter(emptyList(),choosenCharacter)
                        activity?.runOnUiThread() {
                            recyclerView.layoutManager = LinearLayoutManager(activity)
                            recyclerView.adapter = adapter
                        }
                    }
                })
            }

        })
        return view
    }

    fun getCharacterNameList(characterDatas: List<CharacterData>) : List<String>{
        val data = mutableListOf<String>()
        for(characterData in characterDatas){
            data.add(characterData.name)
        }
        return data
    }
}