package com.arwin.goodmusic

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arwin.goodmusic.adapter.AlbumAdapter
import com.arwin.goodmusic.databinding.ActivityMainBinding
import com.arwin.goodmusic.viewmodel.MusicViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var albumAdapter: AlbumAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MusicViewModel::class.java)

        albumAdapter = AlbumAdapter(emptyList())
        binding.rvAlbums.adapter = albumAdapter
        binding.rvAlbums.layoutManager = LinearLayoutManager(this)

        binding.btnSearch.setOnClickListener {
            val artistName = binding.etArtistName.text.toString()
            if (artistName.isNotEmpty()) {
                viewModel.fetchAlbums(artistName)
            }
        }

        viewModel.albums.observe(this, Observer { albums ->
            albumAdapter = AlbumAdapter(albums)
            binding.rvAlbums.adapter = albumAdapter
        })

        viewModel.error.observe(this, Observer { error ->
            binding.tvError.text = error
        })
    }
}