package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_select_service.home
import kotlinx.android.synthetic.main.activity_voice_check.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.SttContainer

import team.sw.everyonetayo.util.TtsSpeaker
import java.util.*

class VoiceCheck : AppCompatActivity() {

    val DURATION:Long = 1000 //액티비티 이동의 1.5초 딜레이
    private var tts: TextToSpeech? = null //tts 사용

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_check)

        setTitle("음성인식 번호 확인")

        yesButton.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(this, WaitingTime::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, DURATION)
        }

        noButton.setOnClickListener{
            Handler().postDelayed({
                val intent = Intent(this, VoiceReader::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, DURATION)
        }

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        //네 버튼
        yesButton.setOnClickListener{
            val intent = Intent(this, Success::class.java)
            startActivity(intent)
            finish()
        }

        //아니요 버튼
        noButton.setOnClickListener{
            val intent = Intent(this, VoiceReader::class.java)
            startActivity(intent)
            finish()
        }


        //음성인식으로 받아온 데이터 화면에 표시
        val confirmStateOfBus:String = (
                SttContainer.instance.sttRepository().recodeString.myString
                        + "번 버스가 맞습니까?"
                )
        TtsSpeaker.instance.speakOut(confirmStateOfBus+"\n위쪽 버튼은 예, 아래쪽 버튼은 아니요")

        confirmTextView.setText(confirmStateOfBus)

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()
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