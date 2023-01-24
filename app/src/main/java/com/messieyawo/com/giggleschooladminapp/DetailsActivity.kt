package com.messieyawo.com.giggleschooladminapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.messieyawo.com.giggleschooladminapp.databinding.ActivityDetailsBinding
import com.messieyawo.com.giggleschooladminapp.model.Teacher

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private lateinit var mTeachers:MutableList<Teacher>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val name:String = intent.getStringExtra("NAMET").toString()
        val description:String = intent.getStringExtra("DESCRIT").toString()
        val postKey:String = intent.getStringExtra("POSTKEY").toString()
        Log.i("KEY", "KEYVAL $postKey")


        binding.nameDetailTextView.setText(name)
        binding.descriptionDetailTextView.setText(description)

        binding.btnUpdate.setOnClickListener {
            uploadFile(postKey)
        }


    }


    private fun uploadFile(postKey: String) {

        binding.progressBarDetail.visibility = View.VISIBLE
        binding.progressBarDetail.isClickable = false
        binding.progressBarDetail.isIndeterminate = true

        val handler = Handler()
        handler.postDelayed({
            binding.progressBarDetail.visibility = View.VISIBLE
            binding.progressBarDetail.isIndeterminate = false
            binding.progressBarDetail.progress = 0
        }, 500)
        Toast.makeText(
            this@DetailsActivity,
            "Post data Upload successful",
            Toast.LENGTH_LONG
        )
            .show()
        val upload = Teacher(
            name = binding.nameDetailTextView.text.toString().trim { it <= ' ' },
            description =  binding.descriptionDetailTextView.text.toString().trim { it <= ' ' }
        )
       // uploadId = mDatabaseRef!!.push().key
        mDatabaseRef!!.child((postKey)).setValue(upload)
        binding.progressBarDetail.visibility = View.INVISIBLE
        // val name = binding.nameEditText.text.toString().trim { it <= ' ' }
      //  openImagesActivity(upload)


    }


}