package com.tpgate.livredesmercenaires

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpgate.livredesmercenaires.adapters.RecyclerCharacterAdapter
import com.tpgate.livredesmercenaires.model.CharacterData
import com.tpgate.livredesmercenaires.model.CharacterResponse
import com.tpgate.livredesmercenaires.services.CharacterCallback
import com.tpgate.livredesmercenaires.services.TheOneAPIService

class InfoCharacter : Fragment() {
    companion object {
        const val TAG = "InfoCharacter"
    }
    private val theOneAPIService : TheOneAPIService = TheOneAPIService()
    private lateinit var navController : NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
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
        return view
    }
}