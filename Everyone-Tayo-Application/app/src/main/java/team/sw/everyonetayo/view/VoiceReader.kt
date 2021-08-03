package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_voice_reader.*
import team.sw.everyonetayo.R

class VoiceReader : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_reader)

        setTitle("음성인식 버스 예약")

        keypad_call.setOnClickListener{
            val intent = Intent(this, KeyPad::class.java)
            startActivity(intent)
            finish()
        }

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
    }


    //뒤로가기 버튼 동작 코드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}