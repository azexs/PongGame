package com.example.pong

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager

class GameActivity: AppCompatActivity(){

    var myPrefs : SharedPreferences? = null
    private var editor : SharedPreferences.Editor? =  null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        myPrefs = this.getSharedPreferences("gameInfo", Context.MODE_PRIVATE)
        editor = myPrefs!!.edit()
        editor!!.putBoolean("pause", false)
        editor!!.apply()
    }

    override fun onResume() {
        super.onResume()
        setContentView(R.layout.game_activity)
    }

    override fun onPause() {
        super.onPause()
        editor!!.putBoolean("pause",true)
        editor!!.apply()
    }



}