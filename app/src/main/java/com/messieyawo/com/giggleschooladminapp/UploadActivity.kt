package com.messieyawo.com.giggleschooladminapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.messieyawo.com.giggleschooladminapp.databinding.ActivityUploadBinding
import com.messieyawo.com.giggleschooladminapp.model.Teacher

class UploadActivity : AppCompatActivity() {
    lateinit var binding: ActivityUploadBinding

    private var mImageUri : Uri? = null
    private var mStorageRef: StorageReference? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mUploadTask: StorageTask<*>? = null
    private val PICK_IMAGE_REQUEST = 1
    lateinit var encodedImage:String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initialize database
        mStorageRef = FirebaseStorage.getInstance().getReference("MyPosts")
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MyPosts")


        binding.upLoadBtn.setOnClickListener {
            if (mUploadTask != null && mUploadTask!!.isInProgress){
                Toast.makeText(this@UploadActivity,
                    "An Upload is Still in Progress",
                    Toast.LENGTH_SHORT).show()
            }
            else{
                uploadFile()
            }
        }



    }



    private fun openImagesActivity(name: Teacher) {
        val intent = Intent(this, MainActivity::class.java)
        val title = name.name
        val description = name.description
        intent.putExtra("title",title)
        intent.putExtra("description",description)
        startActivity(intent)
    }




    private fun uploadFile() {

        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.isClickable = false
        binding.progressBar.isIndeterminate = true

        val handler = Handler()
        handler.postDelayed({
            binding.progressBar.visibility = View.VISIBLE
            binding.progressBar.isIndeterminate = false
            binding.progressBar.progress = 0
        }, 500)
        Toast.makeText(
            this@UploadActivity,
            "Post data Upload successful",
            Toast.LENGTH_LONG
        )
            .show()
        val upload = Teacher(
            name = binding.nameEditText.text.toString().trim { it <= ' ' },
            description =  binding.descriptionEditText.text.toString().trim { it <= ' ' }
        )
        val uploadId = mDatabaseRef!!.push().key
        mDatabaseRef!!.child((uploadId)!!).setValue(upload)
        binding.progressBar.visibility = View.INVISIBLE
        // val name = binding.nameEditText.text.toString().trim { it <= ' ' }
        openImagesActivity(upload)


    }




}