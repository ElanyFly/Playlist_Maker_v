package com.example.playlistmaker

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

        val buttonSearch = findViewById<Button>(R.id.main_search_button)
        val buttonMedia = findViewById<Button>(R.id.main_media_button)
        val buttonPref = findViewById<Button>(R.id.main_pref_button)

        val buttonSearchClickListener : View.OnClickListener = View.OnClickListener {
            Toast.makeText(this@MainActivity, "Нажали Поиск", Toast.LENGTH_SHORT).show()
        }
        buttonSearch.setOnClickListener(buttonSearchClickListener)

        buttonMedia.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажали Медиатеку", Toast.LENGTH_SHORT).show()
        }

        buttonPref.setOnClickListener {
            Toast.makeText(this@MainActivity, "Нажали Настройки", Toast.LENGTH_SHORT).show()
        }

    }
}

