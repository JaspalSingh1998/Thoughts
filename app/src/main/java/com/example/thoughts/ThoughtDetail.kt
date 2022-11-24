package com.example.thoughts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView

class ThoughtDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thought_detail)

        val thoughtTitle = intent.getStringExtra("title")
        val thoughtDetail = intent.getStringExtra("content")
        val subtitle = findViewById<TextView>(R.id.thoughtTitle)
        val textContent = findViewById<TextView>(R.id.thoughtContent)
        val backBtn = findViewById<ImageView>(R.id.imageView)

        subtitle.text = thoughtTitle
        textContent.text = thoughtDetail
        backBtn.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }
    }
}