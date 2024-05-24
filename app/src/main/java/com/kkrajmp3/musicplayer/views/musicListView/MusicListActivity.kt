package com.kkrajmp3.musicplayer.views.musicListView

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.google.gson.Gson
import com.kkrajmp3.musicplayer.MainActivity
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.database.MusicDatabase
import com.kkrajmp3.musicplayer.database.MusicListDao
import com.kkrajmp3.musicplayer.databinding.ActivityMusicListBinding
import com.kkrajmp3.musicplayer.utility.PermissionManager
import com.kkrajmp3.musicplayer.utility.TmkSharePreference
import com.kkrajmp3.musicplayer.views.favouriteView.FavouriteActivity
import com.kkrajmp3.musicplayer.views.musicListView.adapter.MusicListAdapter
import com.kkrajmp3.musicplayer.views.musicListView.adapter.TypeOfMusicAdapter
import com.kkrajmp3.musicplayer.views.musicListView.mvvm.MusicRepository
import com.kkrajmp3.musicplayer.views.musicListView.mvvm.MusicVMFactory
import com.kkrajmp3.musicplayer.views.musicListView.mvvm.MusicViewModel
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.MusicList
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.TypeOfMusicListModel
import com.kkrajmp3.musicplayer.views.playlist.PlaylistActivity
import com.kkrajmp3.musicplayer.views.recentViews.RecentActivity
import java.io.File
import java.io.Serializable
import java.util.Locale
import java.util.concurrent.Callable


class MusicListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMusicListBinding
    private var requestCodeM = 1002
    private var permissionManager: PermissionManager? = null

    private lateinit var musicViewModel: MusicViewModel
    private lateinit var musicVMFactory: MusicVMFactory
    private var musicListDao: MusicListDao? = null
    private lateinit var tmkSharePreference: TmkSharePreference
    private lateinit var adapter: MusicListAdapter

    companion object {
        lateinit var musicList: ArrayList<MusicList>
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_music_list)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
        permissionManager = PermissionManager(this)
        musicVMFactory = MusicVMFactory(MusicRepository(), this)
        tmkSharePreference = TmkSharePreference.getInstance(this)
        musicViewModel = ViewModelProvider(this, musicVMFactory)[MusicViewModel::class.java]
        musicListDao = MusicDatabase.getInstance(this).musicDao()
        getStoragePermission()
        getCategoryItems()
        getAllDataFromDatabase()
        tmkSharePreference.setFirstCallOnboarding(true)
        searchItems()
        listeners()

    }

    private fun listeners() {
        binding.menuIconBtn.setOnClickListener {
            Toast.makeText(this, "This is navigation drawer", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getAllDataFromDatabase() {
        musicViewModel.getAllMusicFromDatabase()
        if (musicViewModel.allAudioSongsList.isNotEmpty()){
            musicList = musicViewModel.allAudioSongsList
            adapter = MusicListAdapter(musicList)
            val layoutManager = LinearLayoutManager(this)
            binding.musicRV.layoutManager = layoutManager
            binding.musicRV.adapter = adapter
            clickAdapterItems(adapter)

        }
    }

    private fun clickAdapterItems(adapter: MusicListAdapter) {
         adapter.setOnCLickItems(object : MusicListAdapter.ClickItems{
             override fun clickMusicItems(musicList: MusicList) {
                 startActivity(Intent(this@MusicListActivity,MainActivity::class.java).putExtra("musicItem",musicList as Serializable))
             }

         })
    }

    private fun getCategoryItems() {
        val listOfItems = ArrayList<TypeOfMusicListModel>()
        listOfItems.add(TypeOfMusicListModel(R.drawable.fabourite_items, "Favourite"))
        listOfItems.add(TypeOfMusicListModel(R.drawable.playlist_items, "Playlist"))
        listOfItems.add(TypeOfMusicListModel(R.drawable.recent_items, "Recent"))
        val adapter = TypeOfMusicAdapter(listOfItems)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.cardItemsRV.layoutManager = layoutManager
        binding.cardItemsRV.adapter = adapter
        adapter.setOnClickItems(object: TypeOfMusicAdapter.ClickOnItems{
            override fun onClickItems(position: Int) {
                when(position){
                    0 -> {
                        startActivity(Intent(this@MusicListActivity, FavouriteActivity::class.java))
                    }
                    1 -> {
                        startActivity(Intent(this@MusicListActivity, PlaylistActivity::class.java))
                    }
                    else -> {
                        startActivity(Intent(this@MusicListActivity, RecentActivity::class.java))
                    }
                }
            }

        })
    }

    private fun searchItems() {
        binding.searchMusicItem.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s!!.isNotEmpty() && s.toString().trim().isNotEmpty()){
                    var searchItems = ArrayList<MusicList>()
                    var filterPattern = s.toString().toLowerCase()
                    musicList.forEach {items ->
                        if (items.title.toLowerCase().contains(filterPattern)){
                            searchItems.add(items)
                        }
                    }
                    if (searchItems.isEmpty()){
                        binding.musicRV.visibility = View.GONE
                        binding.noDataLayout.visibility = View.VISIBLE
                    }else {
                        binding.musicRV.visibility = View.VISIBLE
                        binding.noDataLayout.visibility = View.GONE
                        adapter.updated(searchItems)
                    }
                }else{
                    adapter.updated(musicList)
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })
    }

    private fun getStoragePermission() {
        var permission: String? = null
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
            permission = Manifest.permission.READ_MEDIA_IMAGES
        } else {
            permission = Manifest.permission.WRITE_EXTERNAL_STORAGE
        }
        permissionManager!!.requestPermission(this,
            null,
            permission,
            "Unable to access file without Write Storage permission",
            "You need to allow Storage access in the phone settin",
            requestCodeM,
            Callable {
                getAudioFileFromStorage()
                null
            }

        )
    }

    private fun getAudioFileFromStorage() {
        musicViewModel.getAllSongs(this)
        if (musicViewModel.allAudioSongsList.isNotEmpty()) {
            musicList = musicViewModel.allAudioSongsList
            adapter = MusicListAdapter(musicList)
            val layoutManager = LinearLayoutManager(this)
            binding.musicRV.layoutManager = layoutManager
            binding.musicRV.adapter = adapter
            clickAdapterItems(adapter)
        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodeM) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAudioFileFromStorage()
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.pleaseGrantRequestedPermission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}