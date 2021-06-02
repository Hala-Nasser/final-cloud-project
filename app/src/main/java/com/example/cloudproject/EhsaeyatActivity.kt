package com.example.cloudproject

import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.android.synthetic.main.activity_ehsaeyat.*

val spinnerList = mutableListOf("2000", "2001", "2002", "2003", "2004", "2005", "2006", "2007")

class EhsaeyatActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var darkMood: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ehsaeyat)

        darkMood = intent.getBooleanExtra("darkMood", false)

        yearSpinner!!.onItemSelectedListener = this
        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, spinnerList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        yearSpinner!!.adapter = arrayAdapter

        chart(2168f, 4067f, 3318f, 1050f, "2000", "10603")

    }

    fun chart(
        float1: Float,
        float2: Float,
        float3: Float,
        float4: Float,
        year: String,
        sum: String
    ) {

        Log.e("ha", "enter chart")

        pieChart.setUsePercentValues(true)
        pieChart.setCenterTextSize(10f)

        var desc = Description()
        desc.text = "احصائية جرحى انتفاضة الأقصى لسنة $year"
        desc.textSize = 18f
        desc.textAlign = Paint.Align.CENTER
        desc.setPosition(430f, 1300f)
        pieChart.description = desc

        if (darkMood) {
            Log.e("ha", "dark is on")
            desc.textColor = Color.WHITE
        } else {
            desc.textColor = Color.BLACK
        }

        pieChart.holeRadius = 50f
        pieChart.transparentCircleRadius = 50f

        var value = ArrayList<PieEntry>()
        value.add(PieEntry(float1, "حي"))
        value.add(PieEntry(float2, "معدني"))
        value.add(PieEntry(float3, "غاز"))
        value.add(PieEntry(float4, "متفرقاات"))
        var pieDataSet = PieDataSet(value, "")
        pieDataSet.valueTextSize = 18f
        var pieData = PieData(pieDataSet)

        pieChart.data = pieData
        val colors: ArrayList<Int> = ArrayList()
        colors.add(ColorTemplate.getHoloBlue())
        colors.add(ColorTemplate.rgb("#6E81EC"))
        colors.add(ColorTemplate.rgb("#23369E"))
        colors.add(ColorTemplate.rgb("#59618B"))

        pieDataSet.colors = colors
        pieChart.animateXY(1400, 1400)
        pieChart.centerText = sum
        pieChart.setCenterTextSize(20f)

        val l: Legend = pieChart.legend
        l.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        l.orientation = Legend.LegendOrientation.VERTICAL
        l.setDrawInside(false)
        l.xEntrySpace = 7f
        l.yEntrySpace = 0f
        l.yOffset = 4f
        l.textSize = 14f


        if (darkMood) {
            l.textColor = Color.WHITE
        } else {
            l.textColor = Color.BLACK
        }

        pieChart.setEntryLabelColor(Color.WHITE)
        pieChart.setEntryLabelTextSize(0f)

        pieChart.notifyDataSetChanged()

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        Log.e("ha", "nothing selected")
        chart(2168f, 4067f, 3318f, 1050f, "2000", "10603")
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        Log.e("ha", "item selected")
        var year = spinnerList[p2]
        Log.e("hala", year)

        if (year == "2000") {
            chart(21.68f, 40.67f, 33.18f, 10.50f, "2000", "10603")
        } else if (year == "2001") {
            chart(14.42f, 12.37f, 14.84f, 22.23f, "2001", "6386")
        } else if (year == "2002") {
            chart(16.59f, 2.44f, 5.36f, 19.43f, "2002", "4382")
        } else if (year == "2003") {
            chart(10.10f, 3.27f, 2.15f, 14.40f, "2003", "2992")
        } else if (year == "2004") {
            chart(13.18f, 4.33f, 7.77f, 14.81f, "2004", "4009")
        } else if (year == "2005") {
            chart(2.37f, 2.32f, 1.74f, 3.50f, "2005", "993")
        } else if (year == "2006") {
            chart(4.51f, 3.03f, 1.18f, 9.09f, "2006", "1781")
        } else if (year == "2007") {
            chart(2.14f, 1.74f, .29f, 2.69f, "2007", "686")
        }

    }

}






