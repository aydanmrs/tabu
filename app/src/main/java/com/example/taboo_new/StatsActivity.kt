package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityStatsBinding

class StatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatsBinding
    private var rightCount: Int = 0
    private var wrongCount: Int = 0
    private var passCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

        rightCount = intent.getIntExtra("rightCount", 0)
        wrongCount = intent.getIntExtra("wrongCount", 0)
        passCount = intent.getIntExtra("passCount", 0)

        binding.rightCount.text = "$rightCount"
        binding.wrongCount.text = "$wrongCount"
        binding.passesCount.text = "$passCount"

        binding.backToMenu.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.resultBtn2.setOnClickListener {
            val intent = Intent(this, GameScreenActivity::class.java).apply {
                addGameType(GameType.SINGLE)
                putExtra("time", intent.getIntExtra("time", 0))
                putExtra("passes", intent.getIntExtra("passes", 0))
                putExtra("selectedDifficulty", intent.getStringExtra("selectedDifficulty"))
                putExtra("selectedCategory", intent.getStringExtra("selectedCategory"))
            }
            startActivity(intent)
            finish()
        }
    }
}
