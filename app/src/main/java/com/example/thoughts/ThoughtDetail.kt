package com.example.thoughts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ThoughtDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thought_detail)

        val thoughtTitle = intent.getStringExtra("title")
        val thoughtDetail = intent.getStringExtra("content")
    }
}