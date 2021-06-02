package com.example.cloudproject

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.cloudproject.model.UserPreferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var token: String
    lateinit var db: FirebaseFirestore
    var darkMood = false
    var hijri = false
    var fontSize = 20.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()
        darkMood = intent.getBooleanExtra("darkMood", false)
        hijri = intent.getBooleanExtra("hijri", false)
        fontSize = intent.getDoubleExtra("fontSize", 20.0)
        token = intent.getStringExtra("token")!!

        news_cardview.setOnClickListener {
            val i = Intent(this, NewsActivity::class.java)
            i.putExtra("darkMood", darkMood)
            i.putExtra("hijri", hijri)
            i.putExtra("fontSize", fontSize)
            startActivity(i)
        }

        m3alem_elquds_cardview.setOnClickListener {
            val i = Intent(this, M3alemElqudsActivity::class.java)
            i.putExtra("darkMood", darkMood)
            i.putExtra("hijri", hijri)
            i.putExtra("fontSize", fontSize)
            startActivity(i)
        }

        tareekh_elquds_cardview.setOnClickListener {
            val i = Intent(this, TareekhAlqudsActivity::class.java)
            i.putExtra("darkMood", darkMood)
            i.putExtra("hijri", hijri)
            i.putExtra("fontSize", fontSize)
            startActivity(i)
        }

        alquds_photo_cardview.setOnClickListener {
            val i = Intent(this, AlqudsPhotoActivity::class.java)
            startActivity(i)
        }

        alaqsa_cardview.setOnClickListener {
            val i = Intent(this, AlaqsaActivity::class.java)
            i.putExtra("darkMood", darkMood)
            i.putExtra("hijri", hijri)
            i.putExtra("fontSize", fontSize)
            startActivity(i)
        }

        ehsaeyat_cardview.setOnClickListener {
            val i = Intent(this, EhsaeyatActivity::class.java)
            i.putExtra("darkMood", darkMood)
            i.putExtra("hijri", hijri)
            i.putExtra("fontSize", fontSize)
            startActivity(i)
        }

        settings.setOnClickListener {
            val i = Intent(this, SettingsActivity::class.java)
            i.putExtra("darkMood", darkMood)
            i.putExtra("hijri", hijri)
            i.putExtra("fontSize", fontSize)
            i.putExtra("token", token)
            startActivityForResult(i, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode === Activity.RESULT_OK) {
                hijri = data!!.getBooleanExtra("hij", false)
                darkMood = data!!.getBooleanExtra("dark", true)
                fontSize = data!!.getDoubleExtra("fontChange", 20.0)
                Log.e("onActivityResult", hijri.toString())
                Log.e("onActivityResultDark", darkMood.toString())
            }
            if (resultCode === Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

}