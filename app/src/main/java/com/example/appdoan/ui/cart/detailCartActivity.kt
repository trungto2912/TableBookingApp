package com.example.appdoan.ui.cart

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appdoan.databinding.ActivityDetailCartBinding
import com.example.appdoan.uitel.getProgressDrawable
import com.example.appdoan.uitel.loadImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_cart.*

class detailCartActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailCartBinding
    private lateinit var database : DatabaseReference
    private lateinit var builderAlert : AlertDialog.Builder

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(toolbarDetailcart)



        val nameCart = intent.getStringExtra("name")
        val imgCart = intent.getStringExtra("img")
        val adressCart = intent.getStringExtra("adress")
        val dayArrive = intent.getStringExtra("day")
        val hourArrive = intent.getStringExtra("hour")
        val children = intent.getStringExtra("children")
        val adult = intent.getStringExtra("adult")
        val noteCart = intent.getStringExtra("note")

        binding.nameDetailCart.text = nameCart
        binding.adressDetailCart.text = adressCart
        binding.dayArriveDetail.text = "Ngày đến : $dayArrive"
        binding.hourArriveDetail.text = "Giờ đến : $hourArrive"
        binding.adultDetail.text = "Người lớn : $adult "
        binding.childrenDetail.text = "Trẻ em : $children "
        binding.noteDetail.text = "Ghi chú : $noteCart"
        imgDetailCart.loadImage(imgCart, getProgressDrawable(this))

        backDetailCart.setOnClickListener {
            onBackPressed()
        }
        btnHuy.setOnClickListener {
            clickBtnHuy()
        }

    }
    fun clickBtnHuy(){
        builderAlert = AlertDialog.Builder(this)
        builderAlert.setTitle("Thông báo !!")
            .setMessage("Bạn muốn hủy đặt chỗ ??")
            .setCancelable(true)
            .setPositiveButton("Có"){
                    dialogInterface,it -> deleteCart()
            }
            .setNegativeButton("Không"){dialogInterface,it ->
                dialogInterface.cancel()
            }
            .show()
    }



    private fun deleteCart() {
        val idCart = intent.getStringExtra("idCart")
        database = FirebaseDatabase.getInstance().getReference("Carts")
        if (idCart != null) {
            database.child(idCart).removeValue()
        }
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
        Toast.makeText(this,"Hủy thành công", Toast.LENGTH_SHORT).show()
    }
}