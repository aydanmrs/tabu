package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.taboo_new.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

        val text =
            "Setup: Split into teams and grab your phones. Open the Taboo app and get ready to play."
        val clonIndex = text.indexOf(":")
        if (clonIndex != -1) {
            val spannableString = SpannableString(text)
            val kalinYazi = StyleSpan(Typeface.BOLD)
            spannableString.setSpan(kalinYazi, 0, clonIndex, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.text1.text = spannableString
        } else {
            binding.text2.text = text
        }


        val text2 =
            "Objective: Get your team to guess the word on your screen without using the \"taboo\" words listed below it."
        val clonIndex2 = text2.indexOf(":")
        if (clonIndex2 != -1) {
            val spannableString = SpannableString(text2)
            val kalinYazi = StyleSpan(Typeface.BOLD)
            spannableString.setSpan(kalinYazi, 0, clonIndex2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.text2.text = spannableString
        } else {
            binding.text3.text = text2
        }


        val text3 =
            "Gameplay: One player gives clues without saying the taboo words. The app will buzz if you slip up."
        val clonIndex3 = text3.indexOf(":")
        if (clonIndex3 != -1) {
            val spannableString = SpannableString(text3)
            val kalinYazi = StyleSpan(Typeface.BOLD)
            spannableString.setSpan(kalinYazi, 0, clonIndex3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.text3.text = spannableString
        } else {
            binding.text3.text = text3
        }


        val text4 =
            "Scoring: Earn points for every correct guess. Lose points for using a taboo word or skipping a card."
        val clonIndex4 = text4.indexOf(":")
        if (clonIndex4 != -1) {
            val spannableString = SpannableString(text4)
            val kalinYazi = StyleSpan(Typeface.BOLD)
            spannableString.setSpan(kalinYazi, 0, clonIndex4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.text4.text = spannableString
        } else {
            binding.text4.text = text4
        }


        val text5 =
            "Winning: The team with the most points wins! Enjoy the fun and challenge your friends to a rematch!"
        val clonIndex5 = text5.indexOf(":")
        if (clonIndex5 != -1) {
            val spannableString = SpannableString(text5)
            val kalinYazi = StyleSpan(Typeface.BOLD)
            spannableString.setSpan(kalinYazi, 0, clonIndex5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            binding.text5.text = spannableString
        } else {
            binding.text5.text = text5
        }
    }

    fun back(view: View) {
        val intent = Intent(this@HelpActivity, MenuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        view.context.startActivity(intent)
    }
}