package com.example.cloudproject

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.example.cloudproject.DB.DatabaseHelper
import com.example.cloudproject.adapter.NewsAdapter
import com.example.cloudproject.model.News
import com.example.cloudproject.singlton.MySinglton
import kotlinx.android.synthetic.main.activity_news.*


class NewsActivity : AppCompatActivity(), NewsAdapter.onItemClickListener {

    lateinit var progressDialog: ProgressDialog
    val newsArray = ArrayList<News>()
    lateinit var db: DatabaseHelper
    var fontSize: Double = 20.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        fontSize = intent.getDoubleExtra("fontSize", 20.0)
        db = DatabaseHelper(this)
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    override fun onStart() {
        super.onStart()

        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm.activeNetworkInfo != null && cm.activeNetworkInfo!!.isConnected) {
            fetchData()
            Log.e("hala", "array size no internet" + newsArray.size)
            Log.e("hala", "sql size no internet " + db.getAllNews().size)
        } else {
            progressDialog.dismiss()
            val newsArraySQL = ArrayList<News>()
            progressDialog.dismiss()
            for (item in db.getAllNews()) {
                var news = News(
                    item.author.toString(), item.title.toString(),
                    item.description.toString(), "null"
                )
                newsArraySQL.add(news)
            }
            rv_news.layoutManager = LinearLayoutManager(this)
            rv_news.setHasFixedSize(true)
            val caseAdapter = NewsAdapter(this, newsArraySQL, this)
            rv_news.adapter = caseAdapter

            Log.e("hala", "internet not connected")
            for (i in newsArraySQL) {
                Log.e("hala", i.urlToImage)
            }
        }

    }

    private fun fetchData() {
        //volly library
        val url =
            "https://newsapi.org/v2/everything?q=القدس&from=2021-05-08&to=2021-05-08&sortBy=popularity&apiKey=67cbfd4a72a34cafa27267f91a1b90d1"
        //making a request
        val jsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                progressDialog.dismiss()
                db.deleteAll()

                val newsJsonArray = it.getJSONArray("articles")
                for (i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("description"),
                        newsJsonObject.getString("urlToImage")
                    )
                    db.insertNews(
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("description"), "null"
                    )
                    newsArray.add(news)
                }
                Log.e("hala", "array size" + newsArray.size)
                Log.e("hala", "sql size" + db.getAllNews().size)

                rv_news.layoutManager = LinearLayoutManager(this)
                rv_news.setHasFixedSize(true)
                val caseAdapter = NewsAdapter(this, newsArray, this)
                rv_news.adapter = caseAdapter

            },
            Response.ErrorListener {
                Log.e("hala", "error volley")
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }
        MySinglton.getInstance()!!.addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClick(data: News, position: Int) {
        val i = Intent(this, NewsDetailsActivity::class.java)
        i.putExtra("title", data.title)
        i.putExtra("description", data.description)
        i.putExtra("urlToImage", data.urlToImage)
        i.putExtra("fontSize", fontSize)
        startActivity(i)
    }


}