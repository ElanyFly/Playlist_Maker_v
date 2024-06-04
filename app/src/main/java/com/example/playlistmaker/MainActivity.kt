package com.example.playlistmaker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val searchButton = findViewById<Button>(R.id.main_search_button)
        val mediaButton = findViewById<Button>(R.id.main_media_button)
        val prefButton = findViewById<Button>(R.id.main_pref_button)

        val buttonSearchClickListener : View.OnClickListener = View.OnClickListener {
            Toast.makeText(this@MainActivity, "Нажали Поиск", Toast.LENGTH_SHORT).show()
        }
        searchButton.setOnClickListener(buttonSearchClickListener)

        mediaButton.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажали Медиатеку", Toast.LENGTH_SHORT).show()
        }

        prefButton.setOnClickListener {
            val prefIntent = Intent(this, SettingsActivity::class.java)
            startActivity(prefIntent)
        }

    }
}

