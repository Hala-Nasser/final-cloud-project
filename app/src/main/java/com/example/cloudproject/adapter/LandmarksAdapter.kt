package com.example.cloudproject.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.R
import com.example.cloudproject.model.History
import com.example.cloudproject.model.Landmarks
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*


class LandmarksAdapter (var activity: Activity, var data: MutableList<Landmarks>, var clickListener: onItemClickListener): RecyclerView.Adapter<LandmarksAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img = itemView.image
        val tvName = itemView.title

        fun initialize(data: Landmarks, action: onItemClickListener) {
            Picasso.get().load(data.image).into(img)

            tvName.text = data.name

            itemView.setOnClickListener {
                action.onItemClick(data, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var View: View = LayoutInflater.from(activity).inflate(R.layout.item, parent, false)
        val myHolder: MyViewHolder = MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.initialize(data[position], clickListener)

    }

    interface onItemClickListener {
        fun onItemClick(data: Landmarks, position: Int)
    }


}