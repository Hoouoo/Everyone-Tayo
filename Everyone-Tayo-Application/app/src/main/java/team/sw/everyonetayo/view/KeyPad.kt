package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.support.v7.app.ActionBar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_key_pad.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.SttContainer
import team.sw.everyonetayo.util.TtsSpeaker


class KeyPad : AppCompatActivity() {

    val DURATION:Long = 1000 //액티비티 이동의 1.5초 딜레이
    private var tts: TextToSpeech? = null //tts 사용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_key_pad)

        setTitle("키패드 입력")

        TtsSpeaker.instance.speakOut("번호를 적으세요,위쪽 버튼은 확인, 아래쪽 버튼은 취소")

        check.setOnClickListener{
            SttContainer.instance.sttRepository().recodeString.myString = busnum.text.toString()
            val intent = Intent(this, VoiceCheck::class.java)
            startActivity(intent)
        }

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

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