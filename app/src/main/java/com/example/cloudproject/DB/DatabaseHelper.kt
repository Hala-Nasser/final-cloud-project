package com.example.cloudproject.DB

import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.cloudproject.model.NewsSQL

class DatabaseHelper(activity: Activity) :
        SQLiteOpenHelper(activity, "myDB", null,5) {
    private val db: SQLiteDatabase = this.writableDatabase
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(NewsSQL.TABLE_CREATE)
    }
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("Drop table if exists ${NewsSQL.TABLE_NAME}")
        onCreate(db)
    }
    fun insertNews(author: String, title: String , description: String, urlToImage: String): Boolean {
        val cv = ContentValues()
        cv.put(NewsSQL.COL_AUTHER, author)
        cv.put(NewsSQL.COL_TITLE, title)
        cv.put(NewsSQL.COL_DESCRIPTION, description)
        cv.put(NewsSQL.COL_URLTOIMAGE, urlToImage)
        return db.insert(NewsSQL.TABLE_NAME, null, cv) > 0
    }
    fun getAllNews(): ArrayList<NewsSQL> {
        val data = ArrayList<NewsSQL>()
        val c =
                db.rawQuery("select * from ${NewsSQL.TABLE_NAME} order by ${NewsSQL.COL_ID} desc", null)
        c.moveToFirst()
        while (!c.isAfterLast) {
            val n = NewsSQL(c.getInt(0), c.getString(1), c.getString(2), c.getString(3),c.getString(3))
            data.add(n)
            c.moveToNext()
        }
        c.close()
        return data
    }

    fun deleteAll (){
        db.execSQL("delete from "+ NewsSQL.TABLE_NAME)

    }
}
