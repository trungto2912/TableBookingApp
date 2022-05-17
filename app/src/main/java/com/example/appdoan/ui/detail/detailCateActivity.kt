package com.example.appdoan.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoan.adapter.RestaurantAdapter
import com.example.appdoan.databinding.ActivityDetailCateBinding
import com.example.appdoan.model.Restaurants
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_detail_cate.*
import kotlinx.android.synthetic.main.activity_detail_res.*
import kotlinx.android.synthetic.main.activity_home.*

class detailCateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailCateBinding

    private lateinit var database : DatabaseReference
    private lateinit var RestaurantList : ArrayList<Restaurants>
    private lateinit var rAdapter : RestaurantAdapter
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbarDetailCate)

        val cateName = intent.getStringExtra("cname")
        textCate.text = cateName

        cateDetailRecycler.layoutManager = LinearLayoutManager(this)
        cateDetailRecycler.setHasFixedSize(true)
        RestaurantList = arrayListOf<Restaurants>()
        rAdapter = RestaurantAdapter(this,RestaurantList)
        cateDetailRecycler.adapter = rAdapter
        getResDataFireBase()

        btnBackCateDetail.setOnClickListener {
            onBackPressed()
        }



    }
    private fun getResDataFireBase() {
        val cateID = intent.getStringExtra("cid")
        database = FirebaseDatabase.getInstance().getReference("Restaurants")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                RestaurantList.clear()
                if (snapshot.exists()) {
                    for (resSnap in snapshot.children) {
                        val resData = resSnap.getValue(Restaurants::class.java)
                        if (resData!=null){
                            if(resData.cateID == cateID ){
                                RestaurantList.add(resData!!)
                            }
                        }
                    }
                    cateDetailRecycler.adapter = rAdapter

                }
            }
            override fun onCancelled(error : DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}