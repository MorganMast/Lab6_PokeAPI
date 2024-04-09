package com.example.lab6_pokeapi

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var btnGenPokemon: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var pokemonAdapter: PokemonAdapter
    private val pokemonList = mutableListOf<Pokemon>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGenPokemon = findViewById(R.id.btnGenPokemon)
        recyclerView = findViewById(R.id.recyclerView)

        pokemonAdapter = PokemonAdapter(this, pokemonList)
        recyclerView.adapter = pokemonAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnGenPokemon.setOnClickListener {
            fetchRandomPokemon()
        }
    }

    private fun fetchRandomPokemon() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://pokeapi.co/api/v2/pokemon/${(1..898).random()}")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 5000
                urlConnection.readTimeout = 5000

                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    val inputStream = urlConnection.inputStream
                    val bufferedReader = BufferedReader(InputStreamReader(inputStream))
                    val response = StringBuilder()
                    var inputLine: String?
                    while (bufferedReader.readLine().also { inputLine = it } != null) {
                        response.append(inputLine)
                    }
                    bufferedReader.close()

                    val jsonResponse = JSONObject(response.toString())
                    val name = jsonResponse.getString("name")
                    val imageUrl = jsonResponse.getJSONObject("sprites").getString("front_default")
                    val typesJsonArray = jsonResponse.getJSONArray("types")
                    val types = mutableListOf<String>()
                    for (i in 0 until typesJsonArray.length()) {
                        types.add(typesJsonArray.getJSONObject(i).getJSONObject("type").getString("name"))
                    }
                    val pokedexNumber = jsonResponse.getInt("id")

                    val pokemon = Pokemon(name.capitalize(Locale.ROOT), imageUrl, types, pokedexNumber)
                    pokemonList.add(pokemon)

                    runOnUiThread {
                        pokemonAdapter.notifyDataSetChanged()
                        recyclerView.smoothScrollToPosition(pokemonList.size - 1)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
