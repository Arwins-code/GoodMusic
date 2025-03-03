package com.arwin.goodmusic.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.arwin.goodmusic.R
import com.arwin.goodmusic.model.Album
import com.bumptech.glide.Glide

class AlbumAdapter(private val albumList: List<Album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumName: TextView = itemView.findViewById(R.id.albumName)
        val albumYear: TextView = itemView.findViewById(R.id.albumYear)
        val albumImage: ImageView = itemView.findViewById(R.id.albumImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.albumName.text = album.strAlbum
        holder.albumYear.text = album.intYearReleased
        // Load image using Glide
        Glide
            .with(holder.itemView.context)
            .load(album.strAlbumThumb)
            .into(holder.albumImage)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }
}
