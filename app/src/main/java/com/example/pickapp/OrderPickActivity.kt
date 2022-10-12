package com.example.pickapp

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import com.budiyev.android.codescanner.CodeScanner


//couldn't pass vars to this, left it for later

class OrderPickActivity(intent: Intent) : AppCompatActivity() {
//    private lateinit var makeOrder: makeOrder

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
//        setContentView(R.layout.activity_new)
        val fullOrder = intent.getStringExtra("message_key")
        val fullOrderTextView: TextView = findViewById(R.id.tv_textView)
        Log.d("TAG", intent.toString())
        Log.d("fullOrder", fullOrder.toString())
        Log.d("fullOrderTextView", fullOrderTextView.toString())
    }

    private fun makeOrder(intent: Intent) {
        Log.d("TAG", intent.toString())
        Log.d("ASDFASDFSADF", "ASDFASDFASDFSDFSDFSAD")
    }

}