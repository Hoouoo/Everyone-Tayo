package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_select_service.home
import kotlinx.android.synthetic.main.activity_voice_check.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.SttContainer
import java.util.*

class VoiceCheck : AppCompatActivity(),  TextToSpeech.OnInitListener {

    val DURATION:Long = 1000 //액티비티 이동의 1.5초 딜레이
    private var tts: TextToSpeech? = null //tts 사용

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_check)
        tts = TextToSpeech(this, this)

        setTitle("음성인식 번호 확인")


        home.setOnClickListener{
            // tts 음성 말하기
            speakOut("홈으로")
            // tts 음성 출력 후 액티비티 이동을 위한 딜레이
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, DURATION)
        }

        yesButton.setOnClickListener{
            speakOut("예")
            Handler().postDelayed({
                val intent = Intent(this, WaitingTime::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, DURATION)
        }

        noButton.setOnClickListener{
            speakOut("아니오")
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

        confirmTextView.setText(confirmStateOfBus)
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