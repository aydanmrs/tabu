package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class BeforeGame1Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_before_game1)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    fun AddTeams(view: View) {
        val intent = Intent(
            this@BeforeGame1Activity,
            CreateTeamActivity::class.java
        ).addGameType(GameType.TEAM1)
        startActivity(intent)
    }

    fun ContinueWithoutATeam(view: View) {
        val intent = Intent(
            this@BeforeGame1Activity,
            SelectCategoryActivity::class.java
        ).addGameType(GameType.SINGLE)
        startActivity(intent)
    }

    fun back(view: View) {
        val intent = Intent(
            this@BeforeGame1Activity,
            MenuActivity::class.java
        )
        startActivity(intent)
    }
}