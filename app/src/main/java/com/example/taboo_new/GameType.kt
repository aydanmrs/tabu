package com.example.taboo_new

import android.content.Intent
import android.os.Build
import java.io.Serializable

object GameTypeConstants{
    const val GAME_TYPE = "GAME_TYPE"
}

enum class GameType : Serializable {
    SINGLE,
    TEAM1,
    TEAM2,
}

fun Intent.getGameType() : GameType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getSerializableExtra(GameTypeConstants.GAME_TYPE, GameType::class.java) ?: GameType.SINGLE
} else {
    (getSerializableExtra(GameTypeConstants.GAME_TYPE) as GameType?) ?: GameType.SINGLE
}

fun Intent.putGameType(oldIntent: Intent) = putExtra(GameTypeConstants.GAME_TYPE, oldIntent.getGameType())

fun Intent.addGameType(gameType: GameType) = putExtra(GameTypeConstants.GAME_TYPE, gameType)

