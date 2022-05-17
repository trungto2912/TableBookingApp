package com.example.appdoan.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appdoan.R
import com.example.appdoan.databinding.ActivityAccountBinding
import com.example.appdoan.model.account
import com.example.appdoan.ui.cart.CartActivity
import com.example.appdoan.ui.home.homeActivity

import com.google.android.material.navigation.NavigationBarView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_account.*


class accountActivity : AppCompatActivity() {
    private var backPressedTime = 0L
    private lateinit var binding : ActivityAccountBinding
    private lateinit var database:DatabaseReference
    private lateinit var builderAlert : AlertDialog.Builder
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadAccount()
        binding.btnLogout.setOnClickListener {
           clickBtnlogout()
        }
        botton_nav_account.setOnItemSelectedListener(NavigationBarView.OnItemSelectedListener {
            when(it.itemId){
                R.id.bottom_home -> {
                    val intent = Intent(this, homeActivity::class.java)
                    startActivity(intent)
                }
                R.id.bottom_cart -> {
                    val intent = Intent(this, CartActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        })
    }
    override fun onBackPressed() {
        if(backPressedTime + 2000 > System.currentTimeMillis()){

            this.moveTaskToBack(true)
        }else{
            Toast.makeText(applicationContext,"Bạn muốn thoát ứng dụng", Toast.LENGTH_SHORT).show()
        }
        backPressedTime = System.currentTimeMillis()
    }
    fun loadAccount(){
        database = FirebaseDatabase.getInstance().getReference("isUser")
        database.get().addOnSuccessListener {
            if (it.exists()) {
                for (accSnap in it.children) {
                    val accData = accSnap.getValue(account::class.java)
                    if (accData!= null) {
                        binding.textNameAcc.text = accData.userName
                        binding.textPhoneAcc.text = accData.phone
                        binding.textEmailAcc.text = accData.email
                    }
                }
            }
        }
    }
    fun clickBtnlogout(){
        builderAlert = AlertDialog.Builder(this)
        builderAlert.setTitle("Thông báo !!")
            .setMessage("Bạn muốn đăng xuất ??")
            .setCancelable(true)
            .setPositiveButton("Có"){
                dialogInterface,it -> logoutAcc()
            }
            .setNegativeButton("Không"){dialogInterface,it ->
                dialogInterface.cancel()
            }
            .show()
    }



    fun logoutAcc(){
        database =FirebaseDatabase.getInstance().getReference("isUser")
        database.removeValue()
        val intent = Intent(this,loginActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"Đăng xuất thành công",Toast.LENGTH_SHORT).show()

    }

}