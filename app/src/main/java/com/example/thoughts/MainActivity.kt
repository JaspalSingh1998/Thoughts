package com.example.thoughts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thoughts.databinding.ActivityMainBinding
import com.example.thoughts.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var databse : DatabaseReference
    private lateinit var thoughtRecyclerView: RecyclerView
    private lateinit var thoughtArrayList: ArrayList<Thought>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thoughtRecyclerView = findViewById(R.id.thoughtsRV)
        thoughtRecyclerView.layoutManager = GridLayoutManager(this, 2)
        thoughtRecyclerView.setHasFixedSize(true)

        thoughtArrayList = arrayListOf<Thought>()
        getThoughts()


//        firebaseAuth = FirebaseAuth.getInstance()
//        binding.logout.setOnClickListener {
//            firebaseAuth.signOut()
//            val intent = Intent(this, SignInActivity::class.java)
//            startActivity(intent)
//            Toast.makeText(this, "Successfully Logged out!", Toast.LENGTH_SHORT).show()
//        }
        binding.plus.setOnClickListener {
             val intent1 = Intent(this, AddScreen::class.java)
             startActivity(intent1)
        }
    }

    private fun getThoughts() {
        databse = Firebase.database.getReference("Thought")

        databse.addValueEventListener(object: ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (thoughtSnapshot in snapshot.children) {
                        val thought = thoughtSnapshot.getValue(Thought::class.java)
                        thoughtArrayList.add(thought!!)
                    }
                    var adapter = MyAdapter(thoughtArrayList)
                    thoughtRecyclerView.adapter = adapter
                    adapter.setOnItemClickListener(object : MyAdapter.onItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@MainActivity, ThoughtDetail::class.java)
                            intent.putExtra("title", thoughtArrayList[position].title)
                            intent.putExtra("content", thoughtArrayList[position].content)
                            startActivity(intent)
                        }

                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Error","Something went wrong!")
            }

        })
    }
}