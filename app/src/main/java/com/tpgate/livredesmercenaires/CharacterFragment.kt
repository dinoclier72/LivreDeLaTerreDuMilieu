package com.tpgate.livredesmercenaires

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tpgate.livredesmercenaires.adapters.RecyclerCharacterAdapter
import com.tpgate.livredesmercenaires.model.CharacterResponse
import com.tpgate.livredesmercenaires.services.CharacterCallback
import com.tpgate.livredesmercenaires.services.TheOneAPIService

class CharacterFragment : Fragment() {
    companion object {
        private  const val TAG = "CharacterFragment"
    }
    private lateinit var recyclerView: RecyclerView
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_character, container, false)
        val theOneAPIService : TheOneAPIService = TheOneAPIService()
        theOneAPIService.listAllCharacters(object : CharacterCallback(){
            override fun fireOnResponseOk(data: CharacterResponse) {
                activity?.runOnUiThread() {
                    val adapter = RecyclerCharacterAdapter(data.docs,navController)
                    recyclerView = view.findViewById(R.id.recyclerView)
                    recyclerView.layoutManager = LinearLayoutManager(activity)
                    recyclerView.adapter = adapter
                }
            }

        })
        return view;
    }
}