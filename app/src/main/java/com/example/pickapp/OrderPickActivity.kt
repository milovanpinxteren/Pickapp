package com.example.pickapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.util.Log

class OrderPickActivity(intent: Intent) : AppCompatActivity() {
    private fun makeOrder(intent: Intent) {
        Log.d("TAG", intent.toString())
    }

}