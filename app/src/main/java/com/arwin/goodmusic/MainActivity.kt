package com.arwin.goodmusic

import android.media.MediaPlayer
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arwin.goodmusic.adapter.AlbumAdapter
import com.arwin.goodmusic.databinding.ActivityMainBinding
import com.arwin.goodmusic.repository.MusicRepository
import com.arwin.goodmusic.utils.RetrofitInstance
import com.arwin.goodmusic.viewmodel.MusicViewModel
import com.arwin.goodmusic.viewmodel.MusicViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MusicViewModel
    private lateinit var albumAdapter: AlbumAdapter
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize the repository
        val repository = MusicRepository(RetrofitInstance.api)

        // Create ViewModel using ViewModelFactory
        val viewModelFactory = MusicViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MusicViewModel::class.java]
//        viewModel = ViewModelProvider(this)[MusicViewModel::class.java]
        viewModel.fetchMusicList()
        albumAdapter = AlbumAdapter(emptyList()) { selectedSong ->
            playMusic(selectedSong.src?.id)
        }
        binding.rvAlbums.adapter = albumAdapter
        binding.rvAlbums.layoutManager = LinearLayoutManager(this)

        observeData()
        searchSong()
    }

    private fun observeData() {
        viewModel.song.observe(this) { albums ->
            albumAdapter = albums?.let {
                AlbumAdapter(it) { selectedSong ->
                    playMusic(selectedSong.src?.id)
                }
            }!!
            albumAdapter.updateData(albums)
            binding.rvAlbums.adapter = albumAdapter
        }

        viewModel.error.observe(this) { error ->
            binding.tvError.text = error
            binding.tvError.visibility = if (error.isNotEmpty()) View.VISIBLE else View.GONE
        }
    }

    private fun searchSong() {
        //Search on button
        binding.btnSearch.setOnClickListener {
            val query = binding.etArtistName.text.toString()
            viewModel.filterAlbums(query)
        }
        //Search on edite
        binding.etArtistName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val query = s.toString()
                if (query.isEmpty()) {
                    viewModel.restoreAllAlbums()
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun playMusic(url: String?) {
        if (url != null) {
            mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(url)
            mediaPlayer.prepareAsync() // Prepare asynchronously to not block the UI thread
            mediaPlayer.setOnPreparedListener {
                mediaPlayer.start() // Start playback when ready
            }
            mediaPlayer.setOnErrorListener { mp, what, extra ->
                // Handle error
                Log.e("MediaPlayer", "Error occurred: $what, $extra")
                true
            }
        } else {
            Log.e("MainActivity", "URL is null, cannot play music.")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release() // Release resources when done
    }
}