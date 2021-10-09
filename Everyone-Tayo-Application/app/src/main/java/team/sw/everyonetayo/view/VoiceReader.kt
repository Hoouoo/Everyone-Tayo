package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.support.v7.app.ActionBar
import android.util.Log
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_voice_reader.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.SttContainer
import team.sw.everyonetayo.controller.stt.SttController
import team.sw.everyonetayo.domain.WrappedString
import team.sw.everyonetayo.util.TtsSpeaker

class VoiceReader : AppCompatActivity() {
    var parent:AppCompatActivity =this;
    var result:WrappedString = WrappedString("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_reader)

        TtsSpeaker.instance.speakOut("위쪽 버튼은 음성입력, 아래쪽 버튼은 키패드입력")

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true);


        //음성인식 버튼
        voiceRecodeButton.setOnClickListener(sttListener())

        //키패드 입력하기
        keypad_call.setOnClickListener {
            val intent = Intent(this, KeyPad::class.java)
            startActivity(intent)
        }

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
    
    inner class sttListener : View.OnClickListener{
        override fun onClick(view: View?) {
            val sttController:SttController = SttContainer.instance.sttController()
            sttController.settingSst(parent, result, stateTextView)
            sttController.startRecod()
        }
    }
}


