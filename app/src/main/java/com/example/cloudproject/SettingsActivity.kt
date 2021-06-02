package com.example.cloudproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_settings.*


class SettingsActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var size = arrayOf(12.0, 14.0, 16.0, 18.0, 20.0, 22.0, 24.0, 26.0, 28.0)
    var token: String = ""
    lateinit var db: FirebaseFirestore
    var tareekhChecked = false
    var darkMoodChecked = false
    var fontSize = 20.0
    lateinit var darkSwitch: Switch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        darkSwitch = findViewById<Switch>(R.id.switch1)
        darkMoodChecked = intent.getBooleanExtra("darkMood", false)
        tareekhChecked = intent.getBooleanExtra("hijri", false)
        token = intent.getStringExtra("token")!!

        db = FirebaseFirestore.getInstance()

        tareekhSwitch.isChecked = tareekhChecked
        switch1.isChecked = darkMoodChecked

        //CHANGE HIJRI/MELADI DATE FOR APPLIACTION
        tareekhSwitch.setOnClickListener {
            if (tareekhSwitch.isChecked) {
                tareekhSwitch.isChecked = true
                tareekhChecked = true
            } else {
                tareekhSwitch.isChecked = false
                tareekhChecked = false
            }
            updateHijri(tareekhChecked, token)
        }


        //CHANGE DARK/LIGHT MODE FOR APPLIACTION
        switch1.setOnClickListener {
            if (switch1.isChecked) {
                Log.e("dark", "darkmood on")
                switch1.isChecked = true
                darkMoodChecked = true
            } else {
                Log.e("dark", "darkmood off")
                switch1.isChecked = false
                darkMoodChecked = false
            }
            updateDarkMood(token, darkMoodChecked)
            Log.e("SettingsDark", darkMoodChecked.toString())

        }


        // change font size
        var fontSizeSpinner = findViewById<Spinner>(R.id.fontSizeSpinner)
        fontSizeSpinner!!.onItemSelectedListener = this
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, size)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fontSizeSpinner.adapter = adapter

        var fontSize = intent.getDoubleExtra("fontSize", 20.0)
        Log.e("hala", "shared pref: $fontSize")

        if (fontSize != null) {
            Log.e("hala", "size selected")
            for (i in size.indices) {
                if (size[i] == fontSize) {
                    fontSizeSpinner.setSelection(i)
                }
            }
        } else {
            Log.e("hala", "no size selected")
            fontSizeSpinner.setSelection(4)
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        updateFontSize(token, 20.0)
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        updateFontSize(token, size[p2])
    }

    private fun updateHijri(hijri: Boolean, mytoken: String) {
        Log.e("updateHijri", "enter")
        db.collection("usersPreferences")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val data = document.data
                        val token = data["token"] as String?

                        if (mytoken == token) {
                            val userPref = HashMap<String, Any>()
                            userPref["hijri"] = hijri

                            db!!.collection("usersPreferences")
                                .whereEqualTo(FieldPath.documentId(), id).get()
                                .addOnSuccessListener { querySnapshot ->
                                    db!!.collection("usersPreferences")
                                        .document(querySnapshot.documents.get(0).id)
                                        .update("hijri", hijri)
                                    Log.e("updateHijri", "update success")
                                }.addOnFailureListener { exception ->
                                    Log.e("updateHijri", "update failed")
                                }
                        }
                    }
                }
            }
    }

    private fun updateDarkMood(mytoken: String, darkMood: Boolean) {
        Log.e("updateDark", "enter")
        db.collection("usersPreferences")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val data = document.data
                        val token = data["token"] as String?

                        if (mytoken == token) {
                            val userPref = HashMap<String, Any>()
                            userPref["darkMood"] = darkMood

                            db!!.collection("usersPreferences")
                                .whereEqualTo(FieldPath.documentId(), id).get()
                                .addOnSuccessListener { querySnapshot ->
                                    db!!.collection("usersPreferences")
                                        .document(querySnapshot.documents.get(0).id)
                                        .update("darkMood", darkMood)
                                    if (darkMood) {
                                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                                    } else {
                                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                                    }

                                    Log.e("updateDark", "update success")
                                }.addOnFailureListener { exception ->
                                    Log.e("updateDark", "update failed")
                                }
                        }
                    }
                }
            }

    }

    private fun updateFontSize(mytoken: String, fontSize: Double) {
        Log.e("updateFont", "enter")
        db.collection("usersPreferences")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        val id = document.id
                        val data = document.data
                        val token = data["token"] as String?

                        if (mytoken == token) {
                            val userPref = HashMap<String, Any>()
                            userPref["fontSize"] = fontSize
                            this.fontSize = fontSize

                            db!!.collection("usersPreferences").document(id)
                                .update(userPref)
                                .addOnSuccessListener { querySnapshot ->
                                    Log.e("updateFont", "update done")
                                }.addOnFailureListener { exception ->
                                    Log.e("updateFont", "update failed")
                                }
                        }
                    }
                }
            }
    }

    override fun onBackPressed() {
        Log.e("halaaa", "onBackPress")
        val returnIntent = Intent()
        returnIntent.putExtra("hij", tareekhChecked)
        returnIntent.putExtra("dark", darkSwitch.isChecked)
        returnIntent.putExtra("fontChange", fontSize)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }

}