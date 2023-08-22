package com.example.practica_ec3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class SplachScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splach_screen)

        android.os.Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}