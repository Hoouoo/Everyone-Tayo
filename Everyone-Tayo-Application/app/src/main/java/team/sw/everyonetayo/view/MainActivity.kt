package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import kotlinx.android.synthetic.main.activity_main.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.view.login.LoginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        start_button.setOnClickListener{
            val intent = Intent(this, SelectService::class.java)
            startActivity(intent)
        }


        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

    }
}