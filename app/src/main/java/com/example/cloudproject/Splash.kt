package com.example.cloudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.animation.Animation
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import com.example.cloudproject.model.UserPreferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.math.log

class Splash : AppCompatActivity() {

    lateinit var myToken: String
    lateinit var db: FirebaseFirestore
    var tokens = mutableListOf<String>()
    var darkMood: Boolean = false
    var hijri: Boolean = false
    var fontSize: Double = 20.0
    var userPreferences = mutableListOf<UserPreferences>()

    lateinit var topAnim: Animation
    lateinit var bottomAnim: Animation
    lateinit var img: ImageView
    lateinit var app_name: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        db = FirebaseFirestore.getInstance()

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener {
            Log.e("hala", "enter token func")
            if (!it.isSuccessful) {
                Log.e("hala", "Fetching FCM registration token failed", it.exception)
                return@OnCompleteListener
            }
            myToken = it.result.toString()
            Log.e("hala", "Token: $myToken")

            getUserPreference()

        })

        topAnim = android.view.animation.AnimationUtils.loadAnimation(this, R.anim.top_animation)
        bottomAnim =
            android.view.animation.AnimationUtils.loadAnimation(this, R.anim.bottom_animation)

        img = findViewById(R.id.logo)
        app_name = findViewById(R.id.app_name)

        img.animation = topAnim
        app_name.animation = bottomAnim

        val handler = Handler()

        handler.postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("darkMood", darkMood)
            Log.e("darkmoodsplash", darkMood.toString())

            intent.putExtra("hijri", hijri)
            intent.putExtra("fontSize", fontSize)
            intent.putExtra("token", myToken)
            startActivity(intent)
            finish()
        }, 5000)


    }

    private fun getUserPreference() {
        db.collection("usersPreferences")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val data = document.data
                        val token = data["token"] as String?
                        val darkMood = data["darkMood"] as Boolean
                        val hijri = data["hijri"] as Boolean
                        val fontSize = data["fontSize"] as Double?
                        userPreferences.add(UserPreferences(token!!, darkMood, hijri, fontSize!!))
                        tokens.add(token)
                        if (myToken == token) {
                            this.darkMood = darkMood
                            this.hijri = hijri
                            this.fontSize = fontSize
                            if (darkMood) {
                                Log.e("halasplash", "dark mood on")
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                            } else {
                                Log.e("halasplash", "dark mood off")
                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                            }
                        }
                    }

                    if (myToken in tokens) {
                        Log.e("existToken", "token exist")


                    } else {
                        addUserPreferences(myToken, false, false, 20.0)
                    }
                }
            }
    }


    private fun addUserPreferences(
        token: String,
        darkMood: Boolean,
        hijri: Boolean,
        fontSize: Double
    ) {
        val userPreferences = hashMapOf(
            "token" to token,
            "darkMood" to darkMood,
            "hijri" to hijri,
            "fontSize" to fontSize
        )
        db.collection("usersPreferences")
            .add(userPreferences)
            .addOnSuccessListener { documentReference ->
                Log.e("hala", "added successfully")
            }
            .addOnFailureListener { exception ->
                Log.e("hala", exception.message.toString())
            }
    }

}