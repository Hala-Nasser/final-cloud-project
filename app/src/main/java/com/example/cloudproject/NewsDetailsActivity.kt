package com.example.cloudproject

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import androidx.appcompat.app.AppCompatDelegate
import com.example.cloudproject.model.UserPreferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_alaqsa.*
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.activity_settings.*

class NewsDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val urlToImage = intent.getStringExtra("urlToImage")
        val fontSize = intent.getDoubleExtra("fontSize", 20.0)

        if (fontSize != null) {
            tv_title.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
            tv_description.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        }

        tv_title.text = title
        tv_description.text = description
        Picasso.get().load(urlToImage).into(image)
    }

}