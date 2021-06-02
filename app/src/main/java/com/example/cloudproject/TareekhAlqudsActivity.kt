package com.example.cloudproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.adapter.HistoryAdapter
import com.example.cloudproject.model.History
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_tareekh_alquds.*

class TareekhAlqudsActivity : AppCompatActivity(), HistoryAdapter.onItemClickListener {

    lateinit var db: FirebaseFirestore
    var fontSize: Double = 20.0
    var hijri: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareekh_alquds)

        db = FirebaseFirestore.getInstance()
        fontSize = intent.getDoubleExtra("fontSize", 20.0)
        hijri = intent.getBooleanExtra("hijri", false)
        getHistory()
    }

    private fun getHistory() {
        val historyList = mutableListOf<History>()
        db.collection("History")
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
                        val day = data["day"] as String?
                        val month = data["month"] as String?
                        val year = data["year"] as String?
                        historyList.add(
                            History(
                                id,
                                name!!,
                                day!!,
                                month!!,
                                year!!,
                                content!!,
                                image!!
                            )
                        )
                    }
                    rv_tareekh.layoutManager =
                        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_tareekh.setHasFixedSize(true)
                    val historyAdapter =
                        HistoryAdapter(
                            this,
                            historyList,
                            this
                        )
                    rv_tareekh.adapter = historyAdapter
                }
            }
    }

    override fun onItemClick(data: History, position: Int) {
        val i = Intent(this, TareekhDetailsActivity::class.java)
        i.putExtra("name", data.name)
        i.putExtra("content", data.content)
        i.putExtra("image", data.image)
        i.putExtra("day", data.day)
        i.putExtra("month", data.month)
        i.putExtra("year", data.year)
        i.putExtra("fontSize", fontSize)
        i.putExtra("hijri", hijri)
        startActivity(i)
    }

}