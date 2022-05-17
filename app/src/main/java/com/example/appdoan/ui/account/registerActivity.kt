package com.example.appdoan.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.appdoan.databinding.ActivityRegisterBinding
import com.example.appdoan.model.account

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class registerActivity : AppCompatActivity() {
    private var backPressedTime = 0L
    private lateinit var binding : ActivityRegisterBinding
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun RegisterEmployee(view : View) {
        val upperAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val lowerAlphabet = "abcdefghijklmnopqrstuvwxyz"
        val numbers = "0123456789"
        val alphaNumeric = upperAlphabet + lowerAlphabet + numbers
        val strBuil = StringBuilder()
        val random = Random()
        val length = 5
        for (i in 0 until length) {
            val indexRan = random.nextInt(alphaNumeric.length)
            val randomChar = alphaNumeric[indexRan]
            strBuil.append(randomChar)
        }
        val idUser = strBuil.toString()

        val username = binding.nameRegister.text.toString()
        val phone = binding.phoneRegister.text.toString()
        val email = binding.emailRegister.text.toString()
        val passWord = binding.passRegister.text.toString()
        val repass = binding.repassRegister.text.toString()
        if(!username.isEmpty() && !phone.isEmpty() && !email.isEmpty() && !passWord.isEmpty() && !repass.isEmpty() ){
            if(passWord == repass){
                database = FirebaseDatabase.getInstance().getReference("Accounts")
                val account = account(idUser,username,phone,email,passWord)
                database.child(idUser).setValue(account).addOnSuccessListener {
                    val intent = Intent(this, loginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this,"Đăng ký thành công",Toast.LENGTH_SHORT).show()
                }.addOnFailureListener{
                    Toast.makeText(this,"Thất bại",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this,"Mật khẩu không khớp",Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_SHORT).show()
        }
    }
    fun openloginActivity(view : View) {
        val intent = Intent(this, loginActivity::class.java)
        startActivity(intent)

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