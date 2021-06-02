package com.example.cloudproject

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.MediaController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_alaqsa.*
import kotlinx.android.synthetic.main.activity_news_details.*


class AlaqsaActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alaqsa)

        db = FirebaseFirestore.getInstance()

        var fontSize = intent.getDoubleExtra("fontSize", 20.0)

        aqsaContent.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize.toFloat())

        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()


//        val mediaController = MediaController(this)
//        mediaController.setAnchorView(videoView)
        var str =
            "https://firebasestorage.googleapis.com/v0/b/cloud-project-e56e1.appspot.com/o/Vedio%2F%D8%A7%D9%84%D9%85%D8%B3%D8%AC%D8%AF%20%D8%A7%D9%84%D8%A7%D9%82%D8%B5%D9%89%20_%20%20al-aqsa%20mosque%20(osmo%20pocket%20video%20test).mp4?alt=media&token=5a4ac3eb-7d9c-41a0-a6cc-fa1d32d3fc63"
        var url = Uri.parse(str)
        //videoView.setMediaController(mediaController)
        videoView.setVideoURI(url)
        videoView.requestFocus()
        videoView.start()

        getContent()

    }

    private fun getContent() {
        db.collection("AboutAlAqsa")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.e("hala", "${document.id}")
                        val id = document.id
                        val data = document.data
                        var content = data["content"] as String?
                        progressDialog.dismiss()
                        aqsaContent.text = content.toString()
                    }

                }
            }
    }

}