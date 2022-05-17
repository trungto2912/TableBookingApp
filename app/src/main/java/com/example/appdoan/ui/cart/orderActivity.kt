package com.example.appdoan.ui.cart

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.appdoan.databinding.ActivityOrderBinding
import com.example.appdoan.model.CartData
import com.example.appdoan.model.account
import com.example.appdoan.uitel.getProgressDrawable
import com.example.appdoan.uitel.loadImage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_detail_cate.*
import kotlinx.android.synthetic.main.activity_detail_res.*
import kotlinx.android.synthetic.main.activity_order.*
import java.util.*

class orderActivity : AppCompatActivity(),DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var saveDay = 0
    var saveMonth = 0
    var saveYear = 0
    var saveHour = 0
    var saveMinute = 0
    var numberBig = 0
    var numberSmall = 0

    private lateinit var database : DatabaseReference

    private lateinit var binding : ActivityOrderBinding
    private lateinit var builderAlertCart : AlertDialog.Builder
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getDateTimeCalendar()
        var currentMonth = month + 1
        dataDay.text = "$day-$currentMonth-$year"
        dataTime.text = "$hour"+"h"+"$minute"

        setSupportActionBar(toolbarOrder)

        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val img = intent.getStringExtra("img")
        val adress = intent.getStringExtra("adress")
        val describe = intent.getStringExtra("describe")
        val price = intent.getStringExtra("price")
        val clientTime = intent.getStringExtra("clientTime")
        val discount = intent.getStringExtra("discount")

        nameResOrder.text = name
        adressResOrder.text = adress
        imageResOrder.loadImage(img, getProgressDrawable(this))

        btnBackOrder.setOnClickListener {
            onBackPressed()
        }
        orderNow.setOnClickListener {
            clickBtnOrder()

        }



    }



    private fun getDateTimeCalendar(){
        val cal= Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour= cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    fun choseDay(view : View) {
        getDateTimeCalendar()
        DatePickerDialog(this,this, year, month, day).show()

    }
    fun choseTime(view : View) {
        getDateTimeCalendar()
        TimePickerDialog(this,this, hour, minute, true).show()
    }


    override fun onDateSet(view : DatePicker?, year : Int, month : Int, dayOfMonth : Int) {
        saveDay = dayOfMonth
        saveMonth = month + 1
        saveYear = year
        dataDay.text = "$saveDay-$saveMonth-$saveYear"
    }

    override fun onTimeSet(view : TimePicker?, hourOfDay : Int, minute  : Int) {
        saveHour = hourOfDay
        saveMinute = minute
        dataTime.text = "$saveHour"+"h"+"$saveMinute"
    }

    fun deleteBig(view : View) {
        numberBig = Integer.parseInt(quantityBig.text.toString())
        if(numberBig > 0){
            numberBig--
            quantityBig.text = "$numberBig"
        }
    }
    fun addBig(view : View) {
        numberBig = Integer.parseInt(quantityBig.text.toString())
        numberBig++
        quantityBig.text = "$numberBig"
    }
    fun deleteSmall(view : View) {
        numberSmall = Integer.parseInt(quantitySmall.text.toString())
        if(numberSmall > 0){
            numberSmall--
            quantitySmall.text = "$numberSmall"
        }
    }
    fun addSmall(view : View) {
        numberSmall = Integer.parseInt(quantitySmall.text.toString())
        numberSmall++
        quantitySmall.text = "$numberSmall"
    }
    fun clickBtnOrder(){
        builderAlertCart = AlertDialog.Builder(this)
        builderAlertCart.setTitle("Thông báo !!")
            .setMessage("Bạn có thật muốn đặt chổ ??")
            .setCancelable(true)
            .setPositiveButton("Có"){
                    dialogInterface,it -> loaddatacartFire()
            }
            .setNegativeButton("Không"){dialogInterface,it ->
                dialogInterface.cancel()
            }
            .show()
    }

    private fun loaddatacartFire() {
        val upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowerAlphabet = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val alphaNumeric = upperAlphabet + lowerAlphabet + numbers
        val strBuil = StringBuilder()
        val random = Random()
        val length = 7
        for (i in 0 until length) {
            val indexRan = random.nextInt(alphaNumeric.length)
            val randomChar = alphaNumeric[indexRan]
            strBuil.append(randomChar)
        }
        val idCart = strBuil.toString()
        val idRes = intent.getStringExtra("id")
        val nameRes = intent.getStringExtra("name")
        val imgRes = intent.getStringExtra("img")
        val adressRes = intent.getStringExtra("adress")
        val dayArrive = binding.dataDay.text.toString()
        val hourArrive = binding.dataTime.text.toString()
        val quantityAdult = binding.quantityBig.text.toString()
        val quantityChildren = binding.quantitySmall.text.toString()
        val noteOrder = binding.noteOrder.text.toString()

        database = FirebaseDatabase.getInstance().getReference("isUser")
        database.get().addOnSuccessListener {
            if (it.exists()) {
                for (accSnap in it.children) {
                    val accData = accSnap.getValue(account::class.java)
                    if (accData!= null) {
                        val idUser = accData.idUser


                        database = FirebaseDatabase.getInstance().getReference("Carts")
                        val orderCart = CartData(idCart,idUser,idRes,nameRes,imgRes,adressRes,dayArrive,hourArrive,quantityAdult,quantityChildren,noteOrder)
                        database.child(idCart).setValue(orderCart).addOnSuccessListener {
                            val intent = Intent(this,CartActivity::class.java)
                            startActivity(intent)
                            Toast.makeText(this,"Đặt chỗ thành công", Toast.LENGTH_SHORT).show()
                        }.addOnFailureListener{
                            Toast.makeText(this,"Thất bại", Toast.LENGTH_SHORT).show()
                        }
                    }

                }

            }
        }

    }


}