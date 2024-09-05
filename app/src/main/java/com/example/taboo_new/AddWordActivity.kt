package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityAddWordBinding
import com.google.firebase.firestore.FirebaseFirestore

class AddWordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddWordBinding
    private val db = FirebaseFirestore.getInstance()

    private var difficulty: String? = null
    private var category: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddWordBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

        binding.add.setOnClickListener {
            showDifficultyActivity()
        }
    }

    private fun showDifficultyActivity() {
        val intent = Intent(this@AddWordActivity, DifficultySelectionActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_DIFFICULTY)
    }

    private fun showCategoryActivity() {
        val intent = Intent(this@AddWordActivity, CategorySelectionActivity::class.java)
        startActivityForResult(intent, REQUEST_CODE_CATEGORY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_DIFFICULTY -> {
                    difficulty = data?.getStringExtra("selectedDifficulty")
                    showCategoryActivity()
                }

                REQUEST_CODE_CATEGORY -> {
                    category = data?.getStringExtra("selectedCategory")
                    addWordsToFirestore()
                }
            }
        }
    }

    private fun addWordsToFirestore() {
        val word = binding.word.text.toString().trim()
        val word1 = binding.word1.text.toString().trim()
        val word2 = binding.word2.text.toString().trim()
        val word3 = binding.word3.text.toString().trim()
        val word4 = binding.word4.text.toString().trim()
        val word5 = binding.word5.text.toString().trim()

        if (word.isNotEmpty() && word1.isNotEmpty() && word2.isNotEmpty() && word3.isNotEmpty() && word4.isNotEmpty() && word5.isNotEmpty()) {
            val words = hashMapOf(
                "word" to word,
                "word1" to word1,
                "word2" to word2,
                "word3" to word3,
                "word4" to word4,
                "word5" to word5
            )

            val collectionName = "${difficulty}_${category}"

            db.collection(collectionName).add(words).addOnSuccessListener {
                clearForm()
                Toast.makeText(this, "Words added successfully!", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { e ->
                Toast.makeText(this, "Error adding words: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearForm() {
        binding.word.text.clear()
        binding.word1.text.clear()
        binding.word2.text.clear()
        binding.word3.text.clear()
        binding.word4.text.clear()
        binding.word5.text.clear()
        difficulty = null
        category = null
    }

    fun Home(view: View) {
        val intent = Intent(this@AddWordActivity, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    companion object {
        private const val REQUEST_CODE_DIFFICULTY = 1
        private const val REQUEST_CODE_CATEGORY = 2
    }
}
