package com.example.taboo_new

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.example.taboo_new.databinding.ActivityGameScreenBinding
import com.example.taboo_new.databinding.DialogAlertBinding
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat
import kotlin.math.roundToInt

class GameScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameScreenBinding
    private val db = FirebaseFirestore.getInstance()
    private var time: Int = 0
    private var passes: Int = 0
    private var initialPasses: Int = 0
    private var difficulty: String? = null
    private var category: String? = null
    private var customCountdownTimer: CustomCountdownTimer? = null
    private var score: Int = 0
    private var isPausedForSettings: Boolean = false
    private var currentDialog: Dialog? = null
    private var words: MutableList<List<String>> = mutableListOf()
    private var currentWordsIndex = 0

    private var usedPassCount = 0
    private var wrongCount = 0

    private lateinit var correctButtonSound: MediaPlayer
    private lateinit var wrongButtonSound: MediaPlayer
    private lateinit var passButtonSound: MediaPlayer
    private lateinit var timeUpSound: MediaPlayer

    private val gameType by lazy { intent.getGameType() }

    companion object {
        const val SETTINGS_REQUEST_CODE = 1

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }


        correctButtonSound = MediaPlayer.create(this, R.raw.correct_raw)
        wrongButtonSound = MediaPlayer.create(this, R.raw.wrong_button_sound)
        passButtonSound = MediaPlayer.create(this, R.raw.pass_button_sound)
        timeUpSound = MediaPlayer.create(this, R.raw.oclock_raw)

        time = intent.getIntExtra("time", 0)
        passes = intent.getIntExtra("passes", 0)
        initialPasses = passes
        difficulty = intent.getStringExtra("selectedDifficulty")
        category = intent.getStringExtra("selectedCategory")

        Log.d(
            "GameScreenActivity",
            "Parameters received - Time: $time, Passes: $passes, Difficulty: $difficulty, Category: $category"
        )

        binding.timeTxt.text = "$time"
        binding.number.text = "$passes"
        binding.score.text = "$score"

        startTimerWithTime(time)
        loadWords()

        binding.pause.setOnClickListener {
            pauseTimer()
            showShortCustomDialog()
            binding.pause.visibility = android.view.View.GONE
            binding.resume.visibility = android.view.View.VISIBLE
        }

        binding.resume.setOnClickListener {
            resumeTimer()
            binding.resume.visibility = android.view.View.GONE
            binding.pause.visibility = android.view.View.VISIBLE
        }

        binding.pass.setOnClickListener {
            if (passes > 0) {
                passes -= 1
                usedPassCount += 1
                binding.number.text = "$passes"
                if (passes == 0) {
                    binding.pass.isEnabled = false
                }
                passButtonSound.start()
                nextWords()
            }
        }

        binding.correct.setOnClickListener {
            correctButtonSound.start()
            score += 1
            binding.score.text = "$score"
            nextWords()
        }

        binding.wrong.setOnClickListener {
            wrongButtonSound.start()
            wrongCount += 1
            nextWords()
        }
    }

    private fun loadWords() {
        val collectionName = "${difficulty}_${category}"
        Log.d("GameScreenActivity", "Fetching words from collection: $collectionName")

        db.collection(collectionName)
            .get()
            .addOnSuccessListener { result ->
                words.clear()
                Log.d(
                    "GameScreenActivity",
                    "Successfully fetched words: ${result.size()} documents"
                )
                for (document in result) {
                    val wordsData = document.data
                    val wordList = listOfNotNull(
                        wordsData["word"] as? String,
                        wordsData["word1"] as? String,
                        wordsData["word2"] as? String,
                        wordsData["word3"] as? String,
                        wordsData["word4"] as? String,
                        wordsData["word5"] as? String
                    )
                    if (wordList.isNotEmpty()) {
                        words.add(wordList)
                    }
                }
                if (words.isNotEmpty()) {
                    words.shuffle()
                    displayCurrentWords()
                    Log.d("GameScreenActivity", "Words loaded and displayed")
                } else {
                    clearWords()
                    Log.d("GameScreenActivity", "No words found in the collection")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("GameScreenActivity", "Error fetching words: ${exception.message}")
                showToast("Error fetching words: ${exception.message}")
            }
    }

    private fun nextWords() {
        currentWordsIndex += 1
        if (currentWordsIndex >= words.size) {
            words.shuffle()
            currentWordsIndex = 0
        }
        displayCurrentWords()
    }

    private fun displayCurrentWords() {
        val currentWords = words.getOrNull(currentWordsIndex) ?: listOf("", "", "", "", "", "")
        binding.word.text = currentWords.getOrElse(0) { "" }
        binding.word1.text = currentWords.getOrElse(1) { "" }
        binding.word2.text = currentWords.getOrElse(2) { "" }
        binding.word3.text = currentWords.getOrElse(3) { "" }
        binding.word4.text = currentWords.getOrElse(4) { "" }
        binding.word5.text = currentWords.getOrElse(5) { "" }
    }

    private fun clearWords() {
        binding.word.text = ""
        binding.word1.text = ""
        binding.word2.text = ""
        binding.word3.text = ""
        binding.word4.text = ""
        binding.word5.text = ""
    }

    private fun showShortCustomDialog() {
        val dialogBinding = DialogAlertBinding.inflate(layoutInflater)
        val customDialog = Dialog(this).apply {
            setContentView(dialogBinding.root)
            window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            setCancelable(false)
        }

        customDialog.setOnDismissListener {
            resumeTimer()
            binding.pause.visibility = android.view.View.VISIBLE
            binding.resume.visibility = android.view.View.GONE
        }

        dialogBinding.resume.setOnClickListener {
            resumeTimer()
            customDialog.dismiss()
            binding.pause.visibility = android.view.View.VISIBLE
            binding.resume.visibility = android.view.View.GONE
        }

        dialogBinding.restart.setOnClickListener {
            val intent = Intent(this, GameScreenActivity::class.java).apply {
                putExtra("time", intent.getIntExtra("time", 0))
                putExtra("passes", initialPasses)
                putExtra("selectedCategory", intent.getStringExtra("selectedCategory"))
                putExtra("selectedDifficulty", intent.getStringExtra("selectedDifficulty"))

                when (gameType) {
                    GameType.SINGLE -> {
                        addGameType(GameType.SINGLE)
                    }
                    GameType.TEAM1, GameType.TEAM2 -> {
                        addGameType(GameType.TEAM1)
                        putExtra(ResultActivity.RIGHT_COUNT_T1, 0)
                        putExtra(ResultActivity.WRONG_COUNT_T1, 0)
                        putExtra(ResultActivity.PASS_COUNT_T1, 0)
                    }
                }
            }
            customCountdownTimer?.destroyTimer()
            startActivity(intent)
            finish()
        }



        dialogBinding.setting.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivityForResult(intent, SETTINGS_REQUEST_CODE)
            customDialog.dismiss()
        }

        dialogBinding.quit.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        customDialog.show()
    }



    private fun startTimerWithTime(seconds: Int) {
        val millis = (seconds * 1000).toLong()
        binding.circularProgressBar.max = seconds
        binding.circularProgressBar.progress = seconds

        customCountdownTimer = object : CustomCountdownTimer(millis, 1000) {}
        customCountdownTimer?.onTick = { millisUntilFinished ->
            val secondsLeft = (millisUntilFinished / 1000.0f).roundToInt()
            timerFormat(secondsLeft)
        }
        customCountdownTimer?.onFinish = {
            timerFormat(0)
            try {
                if (timeUpSound != null && ::timeUpSound.isInitialized)
                    timeUpSound.start()
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
            navigateToStatsActivity()
        }
        customCountdownTimer?.startTimer()
    }

    private fun timerFormat(secondsLeft: Int) {
        binding.circularProgressBar.progress = secondsLeft

        val decimalFormat = DecimalFormat("00")
        val timeFormat = decimalFormat.format(secondsLeft)

        binding.timeTxt.text = timeFormat
    }

    private fun pauseTimer() {
        customCountdownTimer?.pauseTimer()
    }

    private fun resumeTimer() {
        customCountdownTimer?.resumeTimer()
    }

    private fun resetGame() {
        score = 0
        passes = initialPasses
        time = intent.getIntExtra("time", 0)
        usedPassCount = 0
        wrongCount = 0

        binding.score.text = "$score"
        binding.number.text = "$passes"
        binding.pass.isEnabled = true

        startTimerWithTime(time)
        loadWords()
    }

    @SuppressLint("PrivateResource")
    private fun navigateToStatsActivity() {
        when (gameType) {
            GameType.SINGLE -> {
                val intent = Intent(this, StatsActivity::class.java).apply {
                    putExtra("rightCount", score)
                    putExtra("wrongCount", wrongCount)
                    putExtra("passCount", usedPassCount)
                    putExtra("time", time)
                    putExtra("passes", initialPasses)
                    putExtra("selectedCategory", category)
                    putExtra("selectedDifficulty", difficulty)
                }
                startActivity(intent)
                finish()
            }

            GameType.TEAM1 -> {
                val dialogView = layoutInflater.inflate(R.layout.alert_dialog, null)
                val dialogBuilder = AlertDialog.Builder(this)
                    .setView(dialogView)
                    .setCancelable(false)
                    .setOnCancelListener {
                    }

                val alertDialog = dialogBuilder.create()

                alertDialog.setCanceledOnTouchOutside(false)

                dialogView.findViewById<AppCompatButton>(R.id.next_button).setOnClickListener {
                    val intent = Intent(this, GameScreenActivity::class.java).apply {
                        addGameType(GameType.TEAM2)
                        putExtra("time", time)
                        putExtra("passes", initialPasses)
                        putExtra("selectedCategory", category)
                        putExtra("selectedDifficulty", difficulty)
                        putExtra(ResultActivity.RIGHT_COUNT_T1, score)
                        putExtra(ResultActivity.WRONG_COUNT_T1, wrongCount)
                        putExtra(ResultActivity.PASS_COUNT_T1, usedPassCount)
                    }
                    customCountdownTimer?.destroyTimer()
                    startActivity(intent)
                    alertDialog.dismiss()
                    finish()
                }

                alertDialog.show()
                alertDialog.window?.setBackgroundDrawableResource(com.google.android.material.R.color.mtrl_btn_transparent_bg_color)
            }


            GameType.TEAM2 -> {
                val intent = Intent(this, ResultActivity::class.java).apply {
                    putExtra(
                        ResultActivity.RIGHT_COUNT_T1,
                        intent.getIntExtra(ResultActivity.RIGHT_COUNT_T1, 0)
                    )
                    putExtra(
                        ResultActivity.WRONG_COUNT_T1,
                        intent.getIntExtra(ResultActivity.WRONG_COUNT_T1, 0)
                    )
                    putExtra(
                        ResultActivity.PASS_COUNT_T1,
                        intent.getIntExtra(ResultActivity.PASS_COUNT_T1, 0)
                    )

                    putExtra(ResultActivity.RIGHT_COUNT_T2, score)
                    putExtra(ResultActivity.WRONG_COUNT_T2, wrongCount)
                    putExtra(ResultActivity.PASS_COUNT_T2, usedPassCount)

                    putExtra("time", time)
                    putExtra("passes", initialPasses)
                    putExtra("selectedCategory", category)
                    putExtra("selectedDifficulty", difficulty)
                }
                customCountdownTimer?.destroyTimer()
                startActivity(intent)
                finish()
            }
        }
    }


    override fun onResume() {
        super.onResume()
        if (isPausedForSettings) {
            pauseTimer()
            showShortCustomDialog()
            isPausedForSettings = false
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (currentDialog != null && currentDialog!!.isShowing) {
            resumeTimer()
            currentDialog?.dismiss()
        } else {
            super.onBackPressed()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_REQUEST_CODE) {
            isPausedForSettings = true
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::correctButtonSound.isInitialized) {
            correctButtonSound.release()
        }
        if (::wrongButtonSound.isInitialized) {
            wrongButtonSound.release()
        }
        if (::passButtonSound.isInitialized) {
            passButtonSound.release()
        }
        if (::timeUpSound.isInitialized) {
            timeUpSound.release()
        }
        customCountdownTimer?.let {
            it.pauseTimer()
            it.destroyTimer()
        }
    }
}
