package com.example.cloudproject

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cloudproject.adapter.ImageAdapter
import com.example.cloudproject.adapter.LandmarksAdapter
import com.example.cloudproject.model.Landmarks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.activity_alaqsa.*
import kotlinx.android.synthetic.main.activity_alquds_photo.*
import kotlinx.android.synthetic.main.activity_m3alem_elquds.*
import java.io.ByteArrayOutputStream
import java.util.*

class AlqudsPhotoActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    var uri: Uri? = null
    lateinit var db: FirebaseFirestore
    lateinit var imageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alquds_photo)

        db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        imageRef = storageRef.child("qudsImages")

        getImages()

        add_image.setOnClickListener {
            goToGallary()
        }

    }

    private fun UploudImage() {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Uploading")
        progressDialog.show()
        var referenc: StorageReference = FirebaseStorage.getInstance().getReference("Images")
            .child("${Date().time}.jpj")
        referenc.putFile(uri!!).addOnCompleteListener { it ->
            if (it.isSuccessful) {
                referenc.downloadUrl.addOnSuccessListener {
                    addImage(it.toString())
                    getImages()
                    progressDialog.dismiss()

                }
            }

        }
    }

    private fun getImages() {
        val imagesList = mutableListOf<String>()
        db.collection("qudsPhoto")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.e("hala", "${document.id} -> ${document.get("image")}")
                        val id = document.id
                        val data = document.data
                        val image = data["image"] as String?
                        imagesList.add(image!!)
                    }
                    rv_photo.layoutManager =
                        LinearLayoutManager(this, RecyclerView.VERTICAL, false)
                    rv_photo.setHasFixedSize(true)
                    val imageAdapter =
                        ImageAdapter(
                            this,
                            imagesList
                        )
                    rv_photo.adapter = imageAdapter
                    imageAdapter.notifyDataSetChanged()
                }

            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                if (data != null) {
                    uri = data.data
                    UploudImage()
                }
            }
        }

    }

    open fun goToGallary() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 100)
    }

    private fun addImage(image: String) {
        Log.e("haalaa", "addImage")
        val img = hashMapOf("image" to image)
        db.collection("qudsPhoto")
            .add(img)
            .addOnSuccessListener { documentReference ->
                Log.e("hala", "image added successfully with id ${documentReference.id}")
                progressDialog?.dismiss()
            }
            .addOnFailureListener { exception ->
                Log.e("hala", exception.message.toString())
                progressDialog?.dismiss()
            }
    }

}