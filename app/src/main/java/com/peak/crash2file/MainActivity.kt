package com.peak.crash2file

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView


private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        request()
    }

    private fun createBtn() {
        findViewById<TextView>(R.id.text).setOnClickListener(View.OnClickListener {
            var a = 10
            var b = 0
            Log.d(TAG, "onCreate: " + a / b)
        })

    }

    fun request() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1001
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (permissions.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            createBtn()
        }
    }

}