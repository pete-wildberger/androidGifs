package com.example.gifgetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.graphics.drawable.Drawable
import android.support.v7.widget.GridLayoutManager
import java.io.InputStream
import android.support.v7.widget.LinearLayoutManager
import android.os.AsyncTask




val TAG:String = "TEXT"
class MainActivity : AppCompatActivity() {
    val gifUrls: ArrayList<String> = ArrayList()
    lateinit var searchString: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Creates a vertical Layout Manager
        gif_list.layoutManager = LinearLayoutManager(this)

        // Access the RecyclerView Adapter and load the data into it
        gif_list.adapter = GifsAdapter(gifUrls, this)

        editText.setOnEditorActionListener { textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                Log.v(TAG, "submit")
                Thread(Runnable {
                    //Do dome Network Request
                    val res = getGifs(editText.text.toString())
                    runOnUiThread {
                        Log.v(TAG, res)
                        //Update UI
                    }
                }).start()
                hideKeyboard(textView)
                handled = true
                }
            handled
        }


    }
    private fun getGifs(input: String):String{
        val connection = URL("http://api.giphy.com/v1/gifs/search?q="+ input + "&api_key=dc6zaTOxFJmzC").openConnection() as HttpURLConnection
        try {
            val data = connection.inputStream.bufferedReader().readText()
            return data
        } finally {
            connection.disconnect()
        }
    }

    fun LoadImageFromWebOperations(url: String): Drawable? {
        try {
            val imageStream = URL(url).content as InputStream
            return Drawable.createFromStream(imageStream, "src name")
        } catch (e: Exception) {
            return null
        }

    }

}
