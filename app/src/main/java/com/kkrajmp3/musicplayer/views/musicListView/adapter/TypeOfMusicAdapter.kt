package com.kkrajmp3.musicplayer.views.musicListView.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kkrajmp3.musicplayer.R
import com.kkrajmp3.musicplayer.views.musicListView.pojoClass.TypeOfMusicListModel

class TypeOfMusicAdapter(private val typeOfMusicListModel: ArrayList<TypeOfMusicListModel>) : RecyclerView.Adapter<TypeOfMusicAdapter.MyViewModel>() {
    private lateinit var clickOnItems: ClickOnItems

    fun setOnClickItems(clickOnItems: ClickOnItems){
        this.clickOnItems = clickOnItems
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewModel {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.type_of_music_list_sample, parent, false)
        return MyViewModel(view)
    }

    override fun getItemCount(): Int {
        return typeOfMusicListModel.size
    }

    override fun onBindViewHolder(holder: MyViewModel, position: Int) {
        val listItems = typeOfMusicListModel[position]
        holder.icon.setImageResource(listItems.imageIcon)
        holder.titleName.text = listItems.titleName
        holder.itemView.setOnClickListener {
            clickOnItems.onClickItems(position)
        }
    }
    class MyViewModel(itemView: View) : RecyclerView.ViewHolder(itemView){
        val icon = itemView.findViewById<ImageView>(R.id.iconID)
        val titleName = itemView.findViewById<TextView>(R.id.itemTextID)
    }

    interface ClickOnItems{
        fun onClickItems(position: Int)
    }
}