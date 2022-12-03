package com.example.thoughts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.thoughts.databinding.ActivityMainBinding
import com.example.thoughts.databinding.ActivitySignInBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var toggle :ActionBarDrawerToggle
    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var databse : DatabaseReference
    private lateinit var databseReference : DatabaseReference
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

        firebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseAuth.currentUser?.uid

        if (uid != null) {
            databseReference = Firebase.database.getReference("User")
            databseReference.child(uid).get().addOnSuccessListener {
                if(it.exists()) {
                    val userNameSp = it.child("name").value
                    binding.welcomeback.text = "Welcome Back, " + userNameSp.toString()
                }
            }
        }

        getThoughts()


        firebaseAuth = FirebaseAuth.getInstance()
        binding.plus.setOnClickListener {
             val intent1 = Intent(this, AddScreen::class.java)
             startActivity(intent1)
        }

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navView : NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        navView.setNavigationItemSelectedListener {
        firebaseAuth.signOut()
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show()
            true
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }


        return super.onOptionsItemSelected(item)
    }
}