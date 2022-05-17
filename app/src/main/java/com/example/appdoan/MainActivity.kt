package com.example.appdoan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.appdoan.model.account
import com.example.appdoan.ui.account.loginActivity
import com.example.appdoan.ui.home.homeActivity

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class MainActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val handter = Handler()
        handter.postDelayed({
            database = FirebaseDatabase.getInstance().getReference("isUser")
            database.get().addOnSuccessListener {
                if (it.exists()) {
                    for (accSnap in it.children) {
                        val accData = accSnap.getValue(account::class.java)
                        if (accData!= null) {
                            val intent = Intent(this, homeActivity::class.java)
                            startActivity(intent)
                            break
                        }
                    }
                }
            }
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)

        }, 1500)

    }
}