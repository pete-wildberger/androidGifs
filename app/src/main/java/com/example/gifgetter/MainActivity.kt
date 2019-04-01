package com.example.gifgetter

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.android.synthetic.main.activity_main.*
import android.util.Log
import android.view.inputmethod.EditorInfo
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder


val TAG:String = "TEXT"
class MainActivity : AppCompatActivity() {
    val gifUrls: ArrayList<String> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Creates a vertical Layout Manager
        gif_list.layoutManager = LinearLayoutManager(this)


        editText.setOnEditorActionListener { textView, action, event ->
            var handled = false
            if (action == EditorInfo.IME_ACTION_DONE) {
                if(gifUrls.size > 0){
                    gifUrls.clear()
                }
                Log.v(TAG, "submit")
                Thread(Runnable {
                    //Do Network Request
                    val query: String = editText.text.toString()
                    val rootResponse = JSONObject(getGifs(URLEncoder.encode(query, "utf-8")))
                    runOnUiThread {
                        parseGiphyJson(rootResponse)
                        // Access the RecyclerView Adapter and load the data into it
                        gif_list.adapter = GifsAdapter(gifUrls, this)
                        Log.v(TAG, "done" + gifUrls.toString())
                    }
                }).start()
                hideKeyboard(textView)
                handled = true
                }
            handled
        }

    }
    private fun parseGiphyJson(rootObject:JSONObject){
        try{
        val dataKey = rootObject.getString("data")
        val dataArr = JSONArray(dataKey)
        for (i in 0 until dataArr.length()) {
            val gif = JSONObject(dataArr.get(i).toString())
            val images = JSONObject(gif.getString("images"))
            val fixedWidth = JSONObject(images.getString("fixed_width"))
            val url = fixedWidth.getString("url")
            gifUrls.add(url)
        }
        } catch (e: JSONException) {
                Toast.makeText(applicationContext, "Something wrong. Try Again!", Toast.LENGTH_SHORT).show()
                e.printStackTrace()
            }

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




