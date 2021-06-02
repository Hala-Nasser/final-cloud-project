package com.example.cloudproject.model


import android.os.Parcel
import android.os.Parcelable

class NewsSQL (var id:Int, var author: String?, var title: String?, var description: String?,
               var urlToImage: String?) :
        Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(urlToImage)
    }
    override fun describeContents(): Int {
        return 0
    }
    companion object CREATOR : Parcelable.Creator<NewsSQL> {
        override fun createFromParcel(parcel: Parcel): NewsSQL {
            return NewsSQL(parcel)
        }

        override fun newArray(size: Int): Array<NewsSQL?> {
            return arrayOfNulls(size)
        }

        val COL_ID = "id"
        val COL_AUTHER = "author"
        val COL_TITLE = "title"
        val COL_DESCRIPTION = "description"
        val COL_URLTOIMAGE = "urlToImage"

        val TABLE_NAME = "News"
        val TABLE_CREATE = "create table $TABLE_NAME " +
                "($COL_ID integer primary key autoincrement," +
                "$COL_AUTHER text not null, $COL_TITLE text not null ," +
                " $COL_DESCRIPTION text not null ," +
                " $COL_URLTOIMAGE text not null )"
    }

}
