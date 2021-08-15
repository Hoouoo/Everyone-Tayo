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


