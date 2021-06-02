package com.example.cloudproject.adapter

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photo_item.view.*


class ImageAdapter (var activity: Activity, var data: MutableList<String>): RecyclerView.Adapter<ImageAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img = itemView.image

        fun initialize(data: String) {
            Picasso.get().load(data).into(img)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var View: View = LayoutInflater.from(activity).inflate(R.layout.photo_item, parent, false)
        val myHolder: MyViewHolder = MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.initialize(data[position])

    }


}