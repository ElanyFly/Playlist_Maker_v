package com.example.playlistmaker.main.presentation

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.playlistmaker.R
import com.example.playlistmaker.databinding.ActivityMainRootBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainRootBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainRootBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)





//        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

//        val buttonSearchClickListener : View.OnClickListener = View.OnClickListener {
//            val searchIntent = Intent(this, SearchActivity::class.java)
//            startActivity(searchIntent)
//        }
//
//        binding.mainSearchButton.setOnClickListener(buttonSearchClickListener)
//
//        binding.mainMediaButton.setOnClickListener {
//            val mediaIntent = Intent(this, MediaActivity::class.java)
//            startActivity(mediaIntent)
//        }
//
//        binding.mainPrefButton.setOnClickListener {
//            val prefIntent = Intent(this, SettingsActivity::class.java)
//            startActivity(prefIntent)
//        }
    }
}

