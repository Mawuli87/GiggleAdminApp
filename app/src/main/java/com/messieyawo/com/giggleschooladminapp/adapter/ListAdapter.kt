package com.messieyawo.com.giggleschooladminapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.messieyawo.com.giggleschooladminapp.DetailsActivity
import com.messieyawo.com.giggleschooladminapp.databinding.RowItemBinding
import com.messieyawo.com.giggleschooladminapp.model.Teacher


class ListAdapter (
    var mContext:Context,
    var teacherList:List<Teacher>,
//    val onItemClickListener:onItemClick
    ): RecyclerView.Adapter<ListAdapter.ListViewHolder>()
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {

        val binding: RowItemBinding = RowItemBinding.inflate(LayoutInflater.from(mContext), parent, false)

        return ListViewHolder(binding)
    }

    interface onItemClick{
        fun moveSelectedItem(name: String?, descrip: String?, decodedBitmap: String)
    }

    override fun getItemCount(): Int =teacherList.size


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val newList = teacherList[position]
        holder.nameT.text = newList.name
        holder.descriT.text = newList.description





//        holder.rowItem.setOnClickListener {
//
//            val name = newList.name
//            val descrip = newList.description
//            val imgUri = newList.imageUrl
//            val postKey = newList.key
//
//            val mIntent = Intent(mContext, DetailsActivity::class.java)
//            mIntent.putExtra("NAMET",name)
//            mIntent.putExtra("DESCRIT",descrip)
//            mIntent.putExtra("POSTKEY",postKey)
//
//            mContext.startActivity(mIntent)
//        }
    }




    inner class ListViewHolder(v: RowItemBinding): RecyclerView.ViewHolder(v.root){

        var  rowItem = v.rowItem
        var nameT = v.nameTextView
        var descriT = v.descriptionTextView

    }

}