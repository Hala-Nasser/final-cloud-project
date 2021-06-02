package com.example.cloudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.adapter.HistoryAdapter
import com.example.cloudproject.adapter.LandmarksAdapter
import com.example.cloudproject.model.History
import com.example.cloudproject.model.Landmarks
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_m3alem_elquds.*
import kotlinx.android.synthetic.main.activity_tareekh_alquds.*

class M3alemElqudsActivity : AppCompatActivity(), LandmarksAdapter.onItemClickListener {
    lateinit var db: FirebaseFirestore
    var fontSize: Double = 20.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_m3alem_elquds)

        fontSize = intent.getDoubleExtra("fontSize", 20.0)

        db = FirebaseFirestore.getInstance()
        getLandmarks()
    }

    private fun getLandmarks() {
        val landmarksList = mutableListOf<Landmarks>()
        db.collection("Landmarks")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.e("hala", "${document.id} -> ${document.get("name")}")
                        val id = document.id
                        val data = document.data
                        val name = data["name"] as String?
                        val image = data["image"] as String?
                        val content = data["content"] as String?
                        landmarksList.add(Landmarks(id, name!!, content!!, image!!))
                    }
                    rv_m3alem.layoutManager =
                        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_m3alem.setHasFixedSize(true)
                    val landmarksAdapter =
                        LandmarksAdapter(
                            this,
                            landmarksList,
                            this
                        )
                    rv_m3alem.adapter = landmarksAdapter
                }
            }
    }

    override fun onItemClick(data: Landmarks, position: Int) {
        val i = Intent(this, M3alemDetailsMainActivity::class.java)
        i.putExtra("name", data.name)
        i.putExtra("content", data.content)
        i.putExtra("image", data.image)
        i.putExtra("fontSize", fontSize)
        startActivity(i)
    }

}