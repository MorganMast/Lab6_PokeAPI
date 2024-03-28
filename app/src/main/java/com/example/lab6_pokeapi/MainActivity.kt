package com.example.lab6_pokeapi

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import android.util.Log
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
    private lateinit var imgvPokemonPic: ImageView
    private lateinit var txtvPokemonName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnGenPokemon = findViewById(R.id.btnGenPokemon)
        imgvPokemonPic = findViewById(R.id.imageView)
        txtvPokemonName = findViewById(R.id.textView)

        btnGenPokemon.setOnClickListener {
            fetchRandomPokemon()
        }
    }

    private fun fetchRandomPokemon() {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val url = URL("https://pokeapi.co/api/v2/pokemon/${(1..898).random()}")
                val urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.connectTimeout = 5000 // 5 seconds timeout
                urlConnection.readTimeout = 5000 // 5 seconds timeout

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

                    runOnUiThread {
                        txtvPokemonName.text = name.capitalize(Locale.ROOT)
                        // Load image using Picasso
                        Picasso.get().load(imageUrl).into(imgvPokemonPic)
                    }
                } else {
                    Log.e("FetchPokemon", "HTTP Error: ${urlConnection.responseCode}")
                }
            } catch (e: Exception) {
                Log.e("FetchPokemon", "Exception: ${e.message}", e)
            }
        }
    }

}
