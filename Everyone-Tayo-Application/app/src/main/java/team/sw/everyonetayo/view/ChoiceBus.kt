package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_choicebus.*
import kotlinx.android.synthetic.main.activity_select_service.home
import team.sw.everyonetayo.R
import java.util.*

class ChoiceBus : AppCompatActivity(), TextToSpeech.OnInitListener {

    val DURATION:Long = 1000 //액티비티 이동의 1.5초 딜레이
    private var tts: TextToSpeech? = null //tts 사용

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choicebus)
        tts = TextToSpeech(this, this)

        setTitle("버스 종류 선택")


        home.setOnClickListener{
            speakOut("홈으로")
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }, DURATION)
        }


        regular_bus.setOnClickListener{
            speakOut("일반 버스")
            Handler().postDelayed({
                val intent = Intent(this, VoiceReader::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }, DURATION)
        }

        village_bus.setOnClickListener{
            speakOut("마을 버스")
            Handler().postDelayed({
                val intent = Intent(this, VoiceReader::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }, DURATION)
        }

        express_bus.setOnClickListener{
            speakOut("급행 버스")
            Handler().postDelayed({
                val intent = Intent(this, VoiceReader::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
            }, DURATION)
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