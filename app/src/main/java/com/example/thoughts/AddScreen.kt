package com.example.thoughts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AddScreen : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_screen)


        val imageView1 = findViewById<ImageView>(R.id.imageView)
        var selectedColor = ""
        val thoughtTitle = findViewById<EditText>(R.id.thoughtTitle)
        val thoughtDescription = findViewById<EditText>(R.id.thoughtContent)
        val saveThoughtButton = findViewById<Button>(R.id.button2)
        val redBg = findViewById<ImageView>(R.id.redBg)
        val blueBg = findViewById<ImageView>(R.id.blueBg)
        val yellowBg = findViewById<ImageView>(R.id.yelloBg)
        val brownBg = findViewById<ImageView>(R.id.browBg)
        val tealBg = findViewById<ImageView>(R.id.tealBg)
        val purpleBg = findViewById<ImageView>(R.id.purpleBg)
        val pinkBg = findViewById<ImageView>(R.id.pinkBg)

        imageView1.setOnClickListener {
            val intent2 = Intent(this, MainActivity::class.java)
            startActivity(intent2)
        }

        saveThoughtButton.setOnClickListener {
            database = Firebase.database.getReference("Thought")
            val thought = Thought(thoughtTitle.text.toString(), thoughtDescription.text.toString(), selectedColor)
            val thoughTitle = thoughtTitle.text.toString()
            database.child(thoughTitle).setValue(thought).addOnSuccessListener {
                Toast.makeText(this, "Thought secured successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }

        redBg.setOnClickListener {
            selectedColor = "#EC3838"
        }

    }
}