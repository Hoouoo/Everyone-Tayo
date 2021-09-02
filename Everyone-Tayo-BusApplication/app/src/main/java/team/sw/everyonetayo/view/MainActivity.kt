package team.sw.everyonetayo.view

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.configuration.PushaConfig
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.controller.login.LoginController
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.login.LoginRepository
import team.sw.everyonetayo.view.login.LoginActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PushaConfig()

//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//        // 화면을 landscape(가로) 화면으로 고정

        login.setOnClickListener{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } //로그인 버튼을 눌렸을 때


        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

    }
}