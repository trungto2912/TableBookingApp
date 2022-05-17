package com.example.appdoan.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoan.adapter.CateAdapter
import com.example.appdoan.adapter.RestaurantAdapter
import com.example.appdoan.databinding.ActivityHomeBinding
import com.example.appdoan.model.CategoryData
import com.example.appdoan.model.Restaurants
import com.example.appdoan.ui.cart.CartActivity
import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_home.*
import com.example.appdoan.R
import com.example.appdoan.model.account
import com.example.appdoan.ui.account.accountActivity

class homeActivity : AppCompatActivity() {
    private var backPressedTime = 0L
    private lateinit var binding : ActivityHomeBinding
    private lateinit var database : DatabaseReference
    private lateinit var categoryList : ArrayList<CategoryData>
    private lateinit var RestaurantList : ArrayList<Restaurants>
    private lateinit var rAdapter : RestaurantAdapter
    private lateinit var cAdapter : CateAdapter
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        displayNameUser()

        botton_nav_home.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
                R.id.bottom_account -> {
                    val intent = Intent(this, accountActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        })

        //load category

        cateRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        cateRecyclerView.setHasFixedSize(true)
        categoryList = arrayListOf<CategoryData>()
        cAdapter = CateAdapter(this,categoryList)
        cateRecyclerView.adapter = cAdapter
        getCateDataFireBase()

        //load restaurant
        resHotRecyclerView.layoutManager = LinearLayoutManager(this)
        resHotRecyclerView.setHasFixedSize(true)
        RestaurantList = arrayListOf<Restaurants>()
        rAdapter = RestaurantAdapter(this,RestaurantList)
        cateRecyclerView.adapter = rAdapter
        getResHotDataFireBase()

    }

    private fun getCateDataFireBase() {
        database = FirebaseDatabase.getInstance().getReference("Categotys")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                categoryList.clear()
                if (snapshot.exists()) {
                    for (cateSnap in snapshot.children) {
                        val cateData = cateSnap.getValue(CategoryData::class.java)
                        categoryList.add(cateData!!)
                    }
                    cateRecyclerView.adapter = cAdapter

                }
            }
            override fun onCancelled(error : DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    private fun getResHotDataFireBase() {
        database = FirebaseDatabase.getInstance().getReference("Restaurants")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                RestaurantList.clear()
                if (snapshot.exists()) {
                    for (resSnap in snapshot.children) {
                        val resData = resSnap.getValue(Restaurants::class.java)
                        if (resData!=null){
                            if(resData.discount == "20%" ){
                                RestaurantList.add(resData!!)
                            }
                        }
                    }
                    resHotRecyclerView.adapter = rAdapter

                }
            }
            override fun onCancelled(error : DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
    fun displayNameUser(){
        database = FirebaseDatabase.getInstance().getReference("isUser")
        database.get().addOnSuccessListener {
            if (it.exists()) {
                for (accSnap in it.children) {
                    val accData = accSnap.getValue(account::class.java)
                    if (accData!= null) {
                        val userName = accData.userName
                        binding.userName.text = "Hi $userName"
                    }
                }
            }
        }
    }


    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){

            this.moveTaskToBack(true)
        }else{
            Toast.makeText(applicationContext,"Bạn muốn thoát ứng dụng",Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}