package com.example.gifgetter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.gif_list_item.view.*
import com.bumptech.glide.Glide


class GifsAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of gif in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.gif_list_item, parent, false))
    }

    // Binds each gif in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        Glide.with(context).load(items.get(position)).into(holder?.gifUrl);
        holder?.gifUrl?.adjustViewBounds = true
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val gifUrl = view.gif_url
}

//private class DownloadImageTask(internal var bmImage: ImageView?) : AsyncTask<String, Void, Drawable>() {
//
//    override fun doInBackground(vararg url: String): Drawable? {
//        try {
//            val iS = URL(url.toString()).getContent() as InputStream
//            return Drawable.createFromStream(iS, "src name")
//        } catch (e: Exception) {
//            return null
//        }
//
//    }
//
//    override fun onPostExecute(result: Drawable?) {
//        bmImage?.setImageDrawable(result)
//    }
//}