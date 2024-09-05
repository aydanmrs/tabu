package com.example.taboo_new

import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityBeforeGame2Binding

class BeforeGame2Activity : AppCompatActivity() {

    private lateinit var binding: ActivityBeforeGame2Binding
    private var selectedTime: Int? = null
    private var selectedPasses: Int? = null
    private var selectedDifficulty: String? = null
    private var selectedTimeView: TextView? = null
    private var selectedPassView: TextView? = null
    private var selectedDifficultyView: View? = null
    private var selectedCategory: String? = null

    private val gameType by lazy { intent.getGameType() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBeforeGame2Binding.inflate(layoutInflater)
        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }


        selectedCategory = intent.getStringExtra("selectedCategory")

        binding.sec30.setOnClickListener { selectTimeOption(binding.sec30, 30) }
        binding.sec60.setOnClickListener { selectTimeOption(binding.sec60, 60) }
        binding.sec90.setOnClickListener { selectTimeOption(binding.sec90, 90) }
        binding.sec120.setOnClickListener { selectTimeOption(binding.sec120, 120) }
        binding.sec150.setOnClickListener { selectTimeOption(binding.sec150, 150) }
        binding.sec180.setOnClickListener { selectTimeOption(binding.sec180, 180) }
        binding.sec210.setOnClickListener { selectTimeOption(binding.sec210, 210) }

        binding.pas1.setOnClickListener { selectPassOption(binding.pas1, 1) }
        binding.pas2.setOnClickListener { selectPassOption(binding.pas2, 2) }
        binding.pas3.setOnClickListener { selectPassOption(binding.pas3, 3) }
        binding.pas4.setOnClickListener { selectPassOption(binding.pas4, 4) }
        binding.pas5.setOnClickListener { selectPassOption(binding.pas5, 5) }
        binding.pas6.setOnClickListener { selectPassOption(binding.pas6, 6) }
        binding.pas7.setOnClickListener { selectPassOption(binding.pas7, 7) }
        binding.pas8.setOnClickListener { selectPassOption(binding.pas8, 8) }
        binding.pas9.setOnClickListener { selectPassOption(binding.pas9, 9) }
        binding.pas10.setOnClickListener { selectPassOption(binding.pas10, 10) }

        binding.circleEasy.setOnClickListener { selectDifficultyOption(binding.circleEasy, "easy") }
        binding.circleMedium.setOnClickListener { selectDifficultyOption(binding.circleMedium, "medium") }
        binding.circleHard.setOnClickListener { selectDifficultyOption(binding.circleHard, "hard") }

        binding.startButton.setOnClickListener { navigateToGameActivity() }
    }

    private fun selectTimeOption(selectedTextView: TextView, seconds: Int) {
        selectedTimeView?.let {
            it.setBackgroundResource(R.drawable.time_bg)
            it.setTextColor(Color.BLACK)
        }
        selectedTextView.setBackgroundResource(R.drawable.time_bg_two)
        selectedTextView.setTextColor(Color.WHITE)
        selectedTimeView = selectedTextView
        selectedTime = seconds
    }

    private fun selectPassOption(selectedTextView: TextView, passes: Int) {
        selectedPassView?.let {
            it.setBackgroundResource(R.drawable.time_bg)
            it.setTextColor(Color.BLACK)
        }
        selectedTextView.setBackgroundResource(R.drawable.time_bg_two)
        selectedTextView.setTextColor(Color.WHITE)
        selectedPassView = selectedTextView
        selectedPasses = passes
    }

    private fun selectDifficultyOption(selectedImageView: View, difficulty: String) {
        selectedDifficultyView?.let {
            it.setBackgroundResource(R.drawable.circles)
        }
        selectedImageView.setBackgroundResource(R.drawable.circles_two)
        selectedDifficultyView = selectedImageView
        selectedDifficulty = difficulty
    }

    private fun navigateToGameActivity() {
        if (selectedTime != null && selectedPasses != null && selectedDifficulty != null) {
            val intent = Intent(this, GameScreenActivity::class.java).apply {
                putGameType(intent)
                putExtra("time", selectedTime)
                putExtra("passes", selectedPasses)
                putExtra("selectedCategory", selectedCategory)
                putExtra("selectedDifficulty", selectedDifficulty)
            }
            startActivity(intent)
            finish()
        } else {
            Toast.makeText(this, "Please select all options", Toast.LENGTH_SHORT).show()
        }
    }

    fun back(view: View) {
        val intent=Intent(this@BeforeGame2Activity, SelectCategoryActivity::class.java)
        view.context.startActivity(intent)
    }
}
