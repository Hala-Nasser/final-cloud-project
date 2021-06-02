package com.example.cloudproject.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.R
import com.example.cloudproject.model.News
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item.view.*

class NewsAdapter(var activity: Activity, var data: MutableList<News>, var clickListener: onItemClickListener) :
        RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val img = itemView.image
        val tvTitle = itemView.title

        fun initialize(data: News, action: onItemClickListener){

            if (data.urlToImage == "null"){
                Log.e("hala","image is null")
                img.scaleType = ImageView.ScaleType.FIT_XY
                img.setImageResource(R.drawable.error)

            }else{
                Log.e("hala","image is not null")
                Picasso.get().load(data.urlToImage).into(img)
            }

            tvTitle.text = data.title

            itemView.setOnClickListener {
                action.onItemClick(data,adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        var View: View = LayoutInflater.from(activity).inflate(R.layout.item,parent,false)
        val myHolder: MyViewHolder = MyViewHolder(View)
        return myHolder
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.initialize(data.get(position), clickListener)

    }
    interface onItemClickListener{
        fun onItemClick(data: News, position: Int)
    }
}
