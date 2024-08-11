package com.example.deber03

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarRecycleView()
    }

    private fun inicializarRecycleView() {
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        val twitterProfileList = listOf<TwitterProfile>(
            TwitterProfile("MrBeast","X Super Official CEO", "@MrBeast",R.drawable.mr_beast_profile_pic),
            TwitterProfile("Elon Musk","", "@elonmusk",R.drawable.elon_musk_profile_pic),
            TwitterProfile("Bill Gates","Sharing things I'm learning through my foundation work and other interests.", "@Bill Gates",R.drawable.bill_gates_profile_pic),
            TwitterProfile("SpaceX","SpaceX designs, manufactures and launches the world’s most advanced rockets and spacecraft", "@SpaceX",R.drawable.spacex_profile_pic),
            TwitterProfile("Jeff Bezos","Amazon. Blue Origin. Washington Post. Bezos Earth Fund. Bezos Academy.", "@JeffBezos",R.drawable.jeff_bezos_profile_pic),
            TwitterProfile("Bitfinex","Bitfinex is the world's leading digital asset trading platform. ", "@bitfinex",R.drawable.bitfinex_profile_pic),
            TwitterProfile("Google","#HeyGoogle", "@Google",R.drawable.google_profile_pic),
            TwitterProfile("NASA","There's space for everybody. ✨", "@NASA",R.drawable.nasa_profile_pic),
            TwitterProfile("Marvel Entertainment","The official account for Marvel comics, movies, games, and more.", "@Marvel",R.drawable.marvel_profile_pic),
            TwitterProfile("DC","The global DC fandom unites here \uD83D\uDCCD", "@DCOfficial",R.drawable.dc_profile_pic),
            TwitterProfile("Ryan Reynolds","owner: @aviationgin - @MintMobile- @maximumeffort - @Wrexham_AFC", "@VancityReynolds",R.drawable.ryan_reynolds_profile_pic),
            TwitterProfile("Tom Cruise","Actor. Producer. Running in movies since 1981.", "@TomCruise",R.drawable.tom_cruise_profile_pic)

        )
        val recyclerViewTwitterProfile = findViewById<RecyclerView>(R.id.id_rv_might_to_follow)
        recyclerViewTwitterProfile.layoutManager = manager
        recyclerViewTwitterProfile.adapter = TwitterProfileAdapter(twitterProfileList)
        recyclerViewTwitterProfile.addItemDecoration(decoration)
    }
}