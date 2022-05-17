package com.example.appdoan.ui.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoan.adapter.RestaurantAdapter
import com.example.appdoan.databinding.ActivityDetailResBinding
import com.example.appdoan.model.Restaurants
import com.example.appdoan.ui.cart.orderActivity
import com.example.appdoan.uitel.getProgressDrawable
import com.example.appdoan.uitel.loadImage
import com.google.firebase.database.*

import kotlinx.android.synthetic.main.activity_detail_res.*


class detailResActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailResBinding

    private lateinit var database : DatabaseReference
    private lateinit var RestaurantList : ArrayList<Restaurants>
    private lateinit var rAdapter : RestaurantAdapter
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailResBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbarDetailRes)

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val img = intent.getStringExtra("img")
        val adress = intent.getStringExtra("adress")
        val describe = intent.getStringExtra("describe")
        val price = intent.getStringExtra("price")
        val clientTime = intent.getStringExtra("clientTime")
        val discount = intent.getStringExtra("discount")


        nameDetail.text = name
        adressDetail.text = adress
        desDetail.text = describe
        disDetail.text = "Giảm giá: $discount"
        priceDetail.text = "$price đ/người"
        timeDetail.text = "Giờ đón khách: $clientTime"
        imgDetail.loadImage(img, getProgressDrawable(this))

        restauLQRecycler.layoutManager = LinearLayoutManager(this)
        restauLQRecycler.setHasFixedSize(true)
        RestaurantList = arrayListOf<Restaurants>()
        rAdapter = RestaurantAdapter(this,RestaurantList)
        restauLQRecycler.adapter = rAdapter
        getResDataFireBase()

        btnOrder.setOnClickListener {
            val intent = Intent(this, orderActivity::class.java)
            intent.putExtra("id",id)
            intent.putExtra("name",name)
            intent.putExtra("img",img)
            intent.putExtra("adress",adress)
            intent.putExtra("describe",describe)
            intent.putExtra("price",price)
            intent.putExtra("clientTime",clientTime)
            intent.putExtra("discount",discount)
            startActivity(intent)

        }
        btnBackDetailRes.setOnClickListener {
            onBackPressed()
        }



    }
    private fun getResDataFireBase() {
        val cateID = intent.getStringExtra("cateID")
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
                    restauLQRecycler.adapter = rAdapter

                }
            }
            override fun onCancelled(error : DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}