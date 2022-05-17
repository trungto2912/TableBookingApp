package com.example.appdoan.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.appdoan.adapter.CartAdapter
import com.example.appdoan.databinding.ActivityCartBinding
import com.example.appdoan.model.CartData
import com.example.appdoan.model.account
import com.example.appdoan.ui.account.accountActivity
import com.example.appdoan.ui.home.homeActivity
import com.example.appdoan.R

import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_cart.*


class CartActivity : AppCompatActivity() {
    private var backPressedTime = 0L
    private lateinit var binding : ActivityCartBinding
    private lateinit var database : DatabaseReference
    private lateinit var CartList : ArrayList<CartData>
    private lateinit var cartAdapter : CartAdapter

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(toolbarCart)
        btnBackCart.setOnClickListener {
            onBackPressed()
        }
        cartRecyclerView.layoutManager = LinearLayoutManager(this)
        cartRecyclerView.setHasFixedSize(true)
        CartList = arrayListOf<CartData>()
        cartAdapter = CartAdapter(this,CartList)
        cartRecyclerView.adapter = cartAdapter
        getCartDataFireBase()

        botton_nav_cart.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> {
                    val intent = Intent(this, homeActivity::class.java)
                    startActivity(intent)
                }
                R.id.bottom_account -> {
                    val intent = Intent(this, accountActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        })
    }

    private fun getCartDataFireBase() {
        database = FirebaseDatabase.getInstance().getReference("isUser")
        database.get().addOnSuccessListener {
            if (it.exists()) {
                for (accSnap in it.children) {
                    val accData = accSnap.getValue(account::class.java)
                    if (accData != null) {
                        //lay idUser từ firebase isUser
                        val idU = accData.idUser

                        database = FirebaseDatabase.getInstance().getReference("Carts")
                        database.addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot : DataSnapshot) {
                                CartList.clear()
                                if (snapshot.exists()) {
                                    for (cartSnap in snapshot.children) {
                                        val cartData = cartSnap.getValue(CartData::class.java)
                                        if(cartData != null){
                                            if(cartData.idUser == idU){
                                                CartList.add(cartData!!)

                                            }
                                        }

                                    }
                                    cartRecyclerView.adapter = cartAdapter
                                }
                            }
                            override fun onCancelled(error : DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                    }
                }
            }
        }


    }

    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){

            this.moveTaskToBack(true)
        }else{
            Toast.makeText(applicationContext,"Bạn muốn thoát ứng dụng", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}