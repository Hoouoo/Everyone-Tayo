package team.sw.everyonetayo.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import team.sw.everyonetayo.R
import team.sw.everyonetayo.util.ToastWithSpeech

class ProcessEnd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("hi2")
        setContentView(R.layout.activity_process_end)
        println("hi")

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()
    }
}