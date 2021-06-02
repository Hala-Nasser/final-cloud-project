package com.example.cloudproject

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_alaqsa.*
import kotlinx.android.synthetic.main.activity_m3alem_details_main.*
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.activity_tareekh_details.*

class M3alemDetailsMainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m3alem_details_main)

        val name = intent.getStringExtra("name")
        val content = intent.getStringExtra("content")
        val image = intent.getStringExtra("image")
        val fontSize = intent.getDoubleExtra("fontSize", 20.0)

        if (fontSize != null) {
            mContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())
        }

        mName.text = name
        mContent.text = content
        Picasso.get().load(image).into(mImage)

    }
}