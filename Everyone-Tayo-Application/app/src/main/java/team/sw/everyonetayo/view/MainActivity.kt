package team.sw.everyonetayo.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import team.sw.everyonetayo.R
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {

    val DURATION:Long = 1000 //액티비티 이동의 1.5초 딜레이
    private var tts: TextToSpeech? = null //tts 사용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        tts = TextToSpeech(this, this)

        start_button.setOnClickListener {
            // tts 음성 말하기
            speakOut("시작하기")
            // tts 음성 출력 후 액티비티 이동을 위한 딜레이
            Handler().postDelayed({
                val intent = Intent(this, SelectService::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }, DURATION)
        }

        // 상단 액션바 숨김
        val actionBar : ActionBar? = supportActionBar
        actionBar?.hide()

    }

    // TextToSpeech.OnInitListener를 상속하는 과정에서 필요한 부분
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    // tts 음성을 출력하기 위한 함수
    private fun speakOut(text_s : String) {
        tts!!.speak(text_s, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    // tts 음성을 초기화 해주기 위한 함수
    public override fun onDestroy() {
        // Shutdown TTS
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()
        }
        super.onDestroy()
    }

}