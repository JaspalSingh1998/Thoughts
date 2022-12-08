package com.example.thoughts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.time.LocalDate
import java.util.*


class AddScreen : AppCompatActivity() {

    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_screen)


        val imageView1 = findViewById<ImageView>(R.id.imageView)
        var selectedColor = "236,56,56"
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
            // Use Kotlin Calendar to get date
            val cal = Calendar.getInstance()

            // Get Date, month, Year from date object.
            val date = cal.get(Calendar.DATE).toString()
            val month = cal.get(Calendar.MONTH).toString()
            val year = cal.get(Calendar.YEAR).toString()

            val createdAt = "$month/$date/$year"

            // Get Firebase database collection
            database = Firebase.database.getReference("Thought")
            val thought = Thought(thoughtTitle.text.toString(), thoughtDescription.text.toString(), selectedColor, createdAt)
            val thoughTitle = thoughtTitle.text.toString()
            database.child(thoughTitle).setValue(thought).addOnSuccessListener {
                // Navigate to Main Activity After successfully Adding new thought
                Toast.makeText(this, "Thought secured successfully!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }.addOnFailureListener {
                Toast.makeText(this, "Something went wrong!", Toast.LENGTH_SHORT).show()
            }
        }

        // Setup click listeners for color images to get color game

        redBg.setOnClickListener {
            selectedColor = "236,56,56"
        }

        blueBg.setOnClickListener {
            selectedColor = "24,123,196"
        }

        yellowBg.setOnClickListener {
            selectedColor = "255,231,16"
        }

        brownBg.setOnClickListener {
            selectedColor = "78,10,10"
        }

        tealBg.setOnClickListener {
            selectedColor = "43,202,135"
        }

        purpleBg.setOnClickListener {
            selectedColor = "115,75,228"
        }

        pinkBg.setOnClickListener {
            selectedColor = "192,24,196"
        }
    }
}