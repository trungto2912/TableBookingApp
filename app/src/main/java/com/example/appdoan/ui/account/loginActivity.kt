package com.example.appdoan.ui.account

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.appdoan.databinding.ActivityLoginBinding
import com.example.appdoan.model.account
import com.example.appdoan.ui.home.homeActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class loginActivity : AppCompatActivity() {
    private var backPressedTime = 0L

    private lateinit var binding : ActivityLoginBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
    fun loginEmployee(view : View) {
        var checkUser = false
        val phoneLogin = binding.phoneLogin.text.toString()
        val passLogin = binding.passlogin.text.toString()
        if(!phoneLogin.isEmpty() && !passLogin.isEmpty()) {
            database = FirebaseDatabase.getInstance().getReference("Accounts")
            database.get().addOnSuccessListener {
                if (it.exists()) {
                    for (accSnap in it.children) {
                        val accData = accSnap.getValue(account::class.java)
                        if (accData != null) {
                            if (accData.phone == phoneLogin) {
                                if (accData.passWord == passLogin) {
                                    //lưu acccount vào firebase
                                    database = FirebaseDatabase.getInstance().getReference("isUser")
                                    val isUser = account(accData.idUser,accData.userName,accData.phone,accData.email,accData.passWord)
                                    val idIsUser = accData.idUser
                                    if (idIsUser != null) {
                                        database.child(idIsUser).setValue(isUser)
                                    }

                                    val intent = Intent(this, homeActivity::class.java)
                                    startActivity(intent)
                                    Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                    checkUser = true
                                    break
                                }
                            } else {
                                checkUser = false
                            }
                        }
                    }
                    if (checkUser == false){
                        Toast.makeText(this, "Số điện thoại hoạc mật khẩu không đúng", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Thất bại", Toast.LENGTH_SHORT).show()
            }
        }
        else {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
        }



    }
    fun openRegisterActivity(view : View) {
        val intent = Intent(this,registerActivity::class.java)
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