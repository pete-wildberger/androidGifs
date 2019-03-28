package com.example.gifgetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.net.HttpURLConnection
import java.net.URL

import android.R
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun getGifs(input: String){
        val connection = URL("http://api.giphy.com/v1/gifs/search?q="+ input + "&api_key=dc6zaTOxFJmzC").openConnection() as HttpURLConnection
        try {
            val data = connection.inputStream.bufferedReader().readText()
            // ... do something with "data"
        } finally {
            connection.disconnect()
        }
    }
}
