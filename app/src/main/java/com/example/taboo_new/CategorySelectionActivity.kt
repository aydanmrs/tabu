package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityCategorySelectionBinding

class CategorySelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategorySelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategorySelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

        // val categories = arrayOf("Food", "Sports", "Animals")

        binding.food.setOnClickListener { returnResult("food") }
        binding.animals.setOnClickListener { returnResult("animals") }
        binding.movies.setOnClickListener { returnResult("movies") }
        binding.mix.setOnClickListener { returnResult("mix") }

    }


    private fun returnResult(category: String) {
        val resultIntent = Intent().apply {
            putExtra("selectedCategory", category)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }
}
