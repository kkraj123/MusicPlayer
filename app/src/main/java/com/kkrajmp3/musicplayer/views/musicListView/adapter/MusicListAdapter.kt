package com.kkrajmp3.musicplayer.views.musicListView.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.MusicList
import kotlin.random.Random

class MusicListAdapter (var musicItems : ArrayList<MusicList>) : RecyclerView.Adapter<MusicListAdapter.MusicListViewHolder>(){
    private lateinit var clickItems: ClickItems
    private val imagesList = listOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4,
        R.drawable.img5,
        R.drawable.img6,
        R.drawable.img8,
        R.drawable.img9,
    )
    fun setOnCLickItems(clickItems: ClickItems){
        this.clickItems = clickItems
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicListViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.music_list_items_layout, parent, false)
        return MusicListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return musicItems.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updated(data : ArrayList<MusicList>){
        musicItems = data
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MusicListViewHolder, position: Int) {
        val items = musicItems[position]
        val randomImages = imagesList.random()
        holder.musicNameTV.text = items.title
        holder.titleTV.text = items.artist
        setTimer(holder,items.duration)
        holder.playBtn.setOnClickListener {
            holder.playBtn.visibility = View.GONE
            holder.pauseBtn.visibility = View.VISIBLE
        }
        holder.pauseBtn.setOnClickListener {
            holder.pauseBtn.visibility = View.GONE
            holder.playBtn.visibility = View.VISIBLE
        }
        holder.startView.setOnClickListener {
            clickItems.clickMusicItems(items)
        }
        Glide.with(holder.itemView.context)
            .load(randomImages).into(holder.imgIV)

    }

    private fun setTimer(holder: MusicListViewHolder, duration: Long) {
        val totalSecond = duration / 1000
        val minutes = totalSecond / 60
        val seconds = totalSecond % 60
        holder.musicTimer.text = "${minutes} : ${seconds}"
    }


    class MusicListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val imgIV = itemView.findViewById<ImageView>(R.id.imgIV)
        val musicNameTV = itemView.findViewById<TextView>(R.id.musicNameTV)
        val titleTV = itemView.findViewById<TextView>(R.id.titleTV)
        val playBtn = itemView.findViewById<ImageView>(R.id.playBtn)
        val musicTimer = itemView.findViewById<TextView>(R.id.musicTimer)
        val pauseBtn = itemView.findViewById<ImageView>(R.id.pauseBtn)
        val startView = itemView.findViewById<RelativeLayout>(R.id.startView)
    }

    interface ClickItems{
        fun clickMusicItems(musicList: MusicList)
    }

}