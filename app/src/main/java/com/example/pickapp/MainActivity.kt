package com.example.pickapp

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import kotlinx.android.synthetic.main.activity_main.*


private const val CAMERA_REQUEST_CODE = 101

class MainActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermissions()
        codeScanner()
    }

    private fun codeScanner() {
        codeScanner = CodeScanner(this, scanner_view)

        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS
            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.CONTINUOUS
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    tv_textView.text = it.text
                    val order = it.text
//                    Losse pick komt niet boven de 30 characters
                    if (it.text.length > 30) {
//                        tv_textView.text = "Order gescand en opgeslagen"
                        setOrderPick(order)
                    } else {
                        Log.d("FAIL", "Scan volledige order")
                    }
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera error: ${it.message}")
                }
            }
        }

        scanner_view.setOnClickListener {
            codeScanner.startPreview()
        }
    }

    private fun setOrderPick(order: String?) {
        Log.d("Order", order.toString())
        var fullOrder = order.toString()
        var lines = fullOrder.lines()
        val orderMap = mutableMapOf<String, String>()
        lines.forEachIndexed { index, orderpick ->
            val pick: String = orderpick
            var aantal: String = pick.get(0).toString()
            val charAfterAantal: Char = pick.get(1)
            Log.d("Aantal", aantal)
            if (aantal.toInt() >= 2 && charAfterAantal.isDigit().not()) {
                Log.d("More than one", pick)
                for (i in 1..aantal.toInt()) {

                    var lastindex = orderMap.keys.last()
                    var newIndex = lastindex + 1
                    orderMap[newIndex] = pick
                    Log.d("Ordermap", orderMap.toString())
                }
            } else {
                Log.d("Maar eentje", pick)
                try {
                    var lastindex = orderMap.keys.last()
                    var newIndex = lastindex + 1
                    orderMap[newIndex] = pick
                } catch (e: NoSuchElementException){
                    orderMap[index.toString()] = pick
                }

                Log.d("Ordermap", orderMap.toString())
            }
        }

        Log.d("Ordermap", orderMap.toString())
        var firstPick = orderMap.get(1.toString())
        tv_textView.text = "Order gescant, ga naar $firstPick"


    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
    }

    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "Camera toestemming nodig voor QR-scanner",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                } else {
                    //succesful request
                }
            }
        }
    }

}