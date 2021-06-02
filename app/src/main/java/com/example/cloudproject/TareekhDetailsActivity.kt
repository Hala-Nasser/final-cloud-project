package com.example.cloudproject

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import com.example.cloudproject.model.UserPreferences
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_alaqsa.*
import kotlinx.android.synthetic.main.activity_news_details.*
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.android.synthetic.main.activity_tareekh_details.*
import org.joda.time.Chronology
import org.joda.time.LocalDate
import org.joda.time.chrono.ISOChronology
import org.joda.time.chrono.IslamicChronology

class TareekhDetailsActivity : AppCompatActivity() {
    lateinit var todayIso: LocalDate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tareekh_details)

        val name = intent.getStringExtra("name")
        val content = intent.getStringExtra("content")
        val image = intent.getStringExtra("image")
        val fontSize = intent.getDoubleExtra("fontSize", 20.0)
        val hijriB = intent.getBooleanExtra("hijri", false)
        val day = intent.getStringExtra("day")!!
        val month = intent.getStringExtra("month")!!
        val year = intent.getStringExtra("year")!!

        tareekhContent.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            fontSize.toFloat()
        )
        tareekhDate.setTextSize(
            TypedValue.COMPLEX_UNIT_SP,
            fontSize.toFloat()
        )

        if (day == "" && month == "" && year == "") {
            tareekhDate.height = 0
            tareekhDate.text = ""
        } else {

            val iso: Chronology = ISOChronology.getInstanceUTC()
            val hijri: Chronology = IslamicChronology.getInstanceUTC()
            var date = ""

            if (hijriB) {

                if (year.toInt() >= 622) {

                    if (day != "" && month != "" && year != "") {
                        todayIso = LocalDate(year.toInt(), month.toInt(), day.toInt(), iso)
                        val todayHijri = LocalDate(todayIso.toDateTimeAtStartOfDay(), hijri)
                        date = todayHijri.toString()
                    } else if (year != "" && month != "") {
                        todayIso = LocalDate(year.toInt(), month.toInt(), 1, iso)
                        val todayHijri = LocalDate(todayIso.toDateTimeAtStartOfDay(), hijri)
                        date = todayHijri.toString().substring(0, 1)
                    } else if (year != "") {
                        todayIso = LocalDate(year.toInt(), 1, 1, iso)
                        val todayHijri = LocalDate(todayIso.toDateTimeAtStartOfDay(), hijri)
                        date = todayHijri.toString().substring(0, 4)
                    }
                    tareekhDate.text = "حدث في $date هجري"
                } else {
                    tareekhDate.text = "حدث قبل الهجرة"
                }

            } else if (!hijriB) {

                if (day != "" && month != "" && year != "") {
                    tareekhDate.text = "حدث في $year-$month-$day ميلادي"
                } else if (year != "" && month != "") {
                    tareekhDate.text = "حدث في شهر $year-$month ميلادي"
                } else if (year != "") {
                    tareekhDate.text = "حدث في سنة $year ميلادي"
                }

            }
        }

        tareekhName.text = name
        tareekhContent.text = content
        Picasso.get().load(image).into(tareekhImage)

    }

}
