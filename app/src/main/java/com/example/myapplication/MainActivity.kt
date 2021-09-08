package com.example.myapplication

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            findViewById<ImageView>(R.id.record_image).setImageDrawable(loadGifFromAssets("recording.gif"))
        }
    }

    override fun onResume() {
        super.onResume()
        val isAccessibilityServiceEnabled = isAccessibilityServiceEnabled(SampleAccessibilityService::class.java)
        if(!isAccessibilityServiceEnabled) {
            showDialog()
        }
    }

    private fun showDialog() {
        if (dialog == null) {
            dialog = AlertDialog.Builder(this)
                .setTitle(R.string.accessibility_dialog_title)
                .setMessage(R.string.accessibility_dialog_message)
                .setPositiveButton(R.string.accessibility_dialog_positive) { dialogInterface , _ ->
                    val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                    startActivity(intent)
                    dialogInterface.dismiss()
                }
                .setCancelable(false)
                .setOnDismissListener {
                    dialog = null
                }
                .create()
        }

        dialog?.show()
    }
}


