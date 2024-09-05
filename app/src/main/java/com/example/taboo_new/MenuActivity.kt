package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityMenuBinding

class MenuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

    }


    fun Rules(view: View) {

        val intent = Intent(this@MenuActivity, HelpActivity::class.java)
        startActivity(intent)
    }

    fun Settings(view: View) {
        val intent = Intent(this@MenuActivity, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun Play(view: View) {
        val intent = Intent(this@MenuActivity, BeforeGame1Activity::class.java)
        startActivity(intent)
    }

    fun AddWords(view: View) {
        val intent = Intent(this@MenuActivity, AddWordActivity::class.java)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}

