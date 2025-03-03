package com.arwin.goodmusic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arwin.goodmusic.databinding.ItemAlbumBinding
import com.arwin.goodmusic.model.DataSong
import com.bumptech.glide.Glide

class AlbumAdapter(
    private var albumList: List<DataSong>,
    private val onItemClick: (DataSong) -> Unit
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    fun updateData(newList: List<DataSong>) {
        albumList = newList
        notifyDataSetChanged() // Notify the adapter to refresh the list
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.binding.albumName.text = album.src?.name ?: ""
        holder.binding.albumYear.text = album.uNm
        Glide
            .with(holder.itemView.context)
            .load(album.img)
            .into(holder.binding.albumImage)
        holder.itemView.setOnClickListener {
            onItemClick(album)
        }
    }

    override fun getItemCount(): Int {
        return albumList.size
    }
}
