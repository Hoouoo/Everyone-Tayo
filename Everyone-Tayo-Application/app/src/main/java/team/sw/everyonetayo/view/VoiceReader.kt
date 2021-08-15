package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_voice_reader.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.SttContainer
import team.sw.everyonetayo.controller.stt.SttController
import team.sw.everyonetayo.domain.WrappedString

class VoiceReader : AppCompatActivity() {
    var parent:AppCompatActivity =this;
    var result:WrappedString = WrappedString("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_reader)
        tts = TextToSpeech(this, this)
        setTitle("음성인식 버스 예약")

        voice_reader.setOnClickListener{
            // tts 음성 말하기
            speakOut("음성 인식. 띵똥 소리 후, 버스 번호를 말해주세요")
            // 음성 인식을 진행하기 위한 함수 필요
        }

        keypad_call.setOnClickListener{
            // tts 음성 말하기
            speakOut("키패드 입력")
            // tts 음성 출력 후 액티비티 이동을 위한 딜레이
            Handler().postDelayed({
                val intent = Intent(this, KeyPad::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                startActivity(intent)
                finish()
            }, DURATION)
        }
        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        
        //음성인식 버튼
        voiceRecodeButton.setOnClickListener(sttListener())
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
    
    inner class sttListener : View.OnClickListener{
        override fun onClick(view: View?) {
            val sttController:SttController = SttContainer.instance.sttController()
            sttController.settingSst(parent, result, stateTextView)
            sttController.startRecod()
        }
    }
}


