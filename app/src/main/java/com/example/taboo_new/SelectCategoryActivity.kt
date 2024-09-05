package com.example.taboo_new

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class SelectCategoryActivity : AppCompatActivity() {

    private val nextIntent by lazy {
        Intent(this, BeforeGame2Activity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_category)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            window.statusBarColor = Color.TRANSPARENT
        }

    }

    fun movies(view: View) {
        openBeforeGame2Activity("movies")
    }

    fun food(view: View) {
        openBeforeGame2Activity("food")
    }

    fun animals(view: View) {
        openBeforeGame2Activity("animals")
    }

    fun mix(view: View) {
        openBeforeGame2Activity("mix")
    }

    private fun openBeforeGame2Activity(category: String) {
        nextIntent.apply {
            putExtra("selectedCategory", category)
            putGameType(intent)
        }
        startActivity(nextIntent)
        finish()
    }


    fun back(view: View) {

        val intent = when (intent.getGameType()) {
            GameType.TEAM1 -> Intent(this@SelectCategoryActivity, CreateTeamActivity::class.java)
            GameType.SINGLE -> Intent(this@SelectCategoryActivity, BeforeGame1Activity::class.java)
            else -> throw IllegalArgumentException("Unknown game type")
        }
        startActivity(intent)
    }
}
