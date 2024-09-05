package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding

    companion object {
        const val RIGHT_COUNT_T1 = "RIGHT_COUNT_T1"
        const val WRONG_COUNT_T1 = "WRONG_COUNT_T1"
        const val PASS_COUNT_T1 = "PASS_COUNT_T1"


        const val RIGHT_COUNT_T2 = "RIGHT_COUNT_T2"
        const val WRONG_COUNT_T2 = "WRONG_COUNT_T2"
        const val PASS_COUNT_T2 = "PASS_COUNT_T2"
    }

    private val t1RightCount by lazy { intent.getIntExtra(RIGHT_COUNT_T1, 0) }
    private val t1WrongCount by lazy { intent.getIntExtra(WRONG_COUNT_T1, 0) }
    private val t1PassCount by lazy { intent.getIntExtra(PASS_COUNT_T1, 0) }

    private val t2RightCount by lazy { intent.getIntExtra(RIGHT_COUNT_T2, 0) }
    private val t2WrongCount by lazy { intent.getIntExtra(WRONG_COUNT_T2, 0) }
    private val t2PassCount by lazy { intent.getIntExtra(PASS_COUNT_T2, 0) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

        binding.rightTeam1.text = "$t1RightCount"
        binding.wrongTeam.text = "$t1WrongCount"
        binding.passTeam.text = "$t1PassCount"

        binding.rightTeam2.text = "$t2RightCount"
        binding.wrongTeam2.text = "$t2WrongCount"
        binding.passTeam2.text = "$t2PassCount"


        when {
            t1RightCount == t2RightCount -> {
                binding.teamOneWin.visibility = View.GONE
                binding.teamTwoWin.visibility = View.GONE
                binding.teamDraw.visibility = View.VISIBLE
            }

            t1RightCount > t2RightCount -> {
                binding.teamOneWin.visibility = View.VISIBLE
                binding.teamTwoWin.visibility = View.GONE
                binding.teamDraw.visibility = View.GONE

            }

            t1RightCount < t2RightCount -> {
                binding.teamOneWin.visibility = View.GONE
                binding.teamTwoWin.visibility = View.VISIBLE
                binding.teamDraw.visibility = View.GONE

            }
        }

        binding.btnRestart.setOnClickListener {
            val intent = Intent(this, GameScreenActivity::class.java).apply {
                addGameType(GameType.TEAM1)
                putExtra("time", intent.getIntExtra("time", 0))
                putExtra("passes", intent.getIntExtra("passes", 0))
                putExtra("selectedDifficulty", intent.getStringExtra("selectedDifficulty"))
                putExtra("selectedCategory", intent.getStringExtra("selectedCategory"))
            }
            startActivity(intent)
            finish()
        }


    }

    fun back_to_menu(view: View) {
        val intent = Intent(this@ResultActivity, MenuActivity::class.java)
        startActivity(intent)
    }
}