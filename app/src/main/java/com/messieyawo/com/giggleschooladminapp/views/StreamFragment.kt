package com.messieyawo.com.giggleschooladminapp.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.messieyawo.com.giggleschooladminapp.R
import com.messieyawo.com.giggleschooladminapp.UploadActivity
import com.messieyawo.com.giggleschooladminapp.adapter.ListAdapter
import com.messieyawo.com.giggleschooladminapp.databinding.ContentStreamBinding
import com.messieyawo.com.giggleschooladminapp.databinding.FragmentStreamBinding
import com.messieyawo.com.giggleschooladminapp.model.Teacher


class StreamFragment : Fragment() {

    private var mStorage: FirebaseStorage? = null
    private var mDatabaseRef: DatabaseReference? = null
    private var mDBListener: ValueEventListener? = null
    private lateinit var mTeachers:MutableList<Teacher>
    private lateinit var listAdapter: ListAdapter
    lateinit var bindingContent: ContentStreamBinding

    lateinit var binding: FragmentStreamBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = FragmentStreamBinding.inflate(layoutInflater)
        bindingContent = ContentStreamBinding.inflate(layoutInflater)

        //hide views




        /**set adapter*/
        bindingContent.mRecyclerView.setHasFixedSize(true)
        bindingContent.mRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        // bindingContent.myDataLoaderProgressBar.visibility = View.VISIBLE
        mTeachers = ArrayList()
        listAdapter = ListAdapter(requireActivity(),mTeachers)

        /**set Firebase Database*/
        mStorage = FirebaseStorage.getInstance()
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("MyPosts")
        mDBListener = mDatabaseRef!!.addValueEventListener(object : ValueEventListener{
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireActivity(),error.message, Toast.LENGTH_SHORT).show()
                bindingContent.progressCircle.visibility = View.INVISIBLE

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                mTeachers.clear()
                for (teacherSnapshot in snapshot.children){
                    val upload = teacherSnapshot.getValue(Teacher::class.java)
                    upload!!.key = teacherSnapshot.key
                    mTeachers.add(0,upload)
                    Log.i("DATA",upload.toString())

                    listAdapter = ListAdapter(requireActivity(),mTeachers)
                    bindingContent.mRecyclerView.adapter = listAdapter


                }
                listAdapter.notifyDataSetChanged()
                bindingContent.progressCircle.visibility = View.GONE

            }

        })

        bindingContent.buttonUpload.setOnClickListener {
            val intent = Intent(requireActivity(), UploadActivity::class.java)
            startActivity(intent)
        }

        return bindingContent.root
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabaseRef!!.removeEventListener(mDBListener!!)
    }







}