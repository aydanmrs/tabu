package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class CreateTeamActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_team)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun Next(view: View) {
        val nextIntent = Intent(
            this@CreateTeamActivity,
            SelectCategoryActivity::class.java
        ).putGameType(intent)
        startActivity(nextIntent)
    }

    fun back(view: View) {
        val intent = Intent(this@CreateTeamActivity, BeforeGame1Activity::class.java)
        startActivity(intent)
    }
}