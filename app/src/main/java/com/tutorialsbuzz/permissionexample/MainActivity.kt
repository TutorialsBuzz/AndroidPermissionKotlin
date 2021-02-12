package com.tutorialsbuzz.permissionexample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {

    private var label: TextView? = null

    companion object {
        const val REQUEST_WRITE_PERMISSION = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        label = findViewById(R.id.label)
        val requestBtn = findViewById<Button>(R.id.requestBtn)
        requestBtn.setOnClickListener { v: View? ->
            if (checkPermissionForReadExtertalStorage()) {
                // Permission Already Granted
                label?.setText(R.string.permission_granted)
            } else {
                // Make Permission Request
                requestPermission()
            }
        }
    }

    fun checkPermissionForReadExtertalStorage(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        } else false
    }

    private fun requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_WRITE_PERMISSION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.size > 0) {
            if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                // permission granted
                label?.setText(R.string.permission_granted)
            } else {
                // permission denied
                label?.setText(R.string.permission_denied)
            }
        }
    }
}