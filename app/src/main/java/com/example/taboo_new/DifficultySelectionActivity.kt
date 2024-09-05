package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityDifficultySelectionBinding

class DifficultySelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDifficultySelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDifficultySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

        val difficulties = arrayOf("Easy", "Medium", "Hard")

        binding.button.setOnClickListener { returnResult("easy") }
        binding.button2.setOnClickListener { returnResult("medium") }
        binding.button3.setOnClickListener { returnResult("hard") }
    }

    private fun returnResult(difficulty: String) {
        val resultIntent = Intent().apply {
            putExtra("selectedDifficulty", difficulty)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
