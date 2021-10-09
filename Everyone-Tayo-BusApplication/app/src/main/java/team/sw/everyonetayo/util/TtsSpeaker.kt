package team.sw.everyonetayo.util

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

class TtsSpeaker : TextToSpeech.OnInitListener {

    companion object{
        val instance:TtsSpeaker = TtsSpeaker()
    }

    private var tts: TextToSpeech? = null //tts 사용
    var isReady:Boolean = false

    constructor(){
        tts = TextToSpeech(ApplicationContext.context(), this)
        tts!!.setSpeechRate(0.99f);
        tts!!.setPitch(1.01f)
    }

    // TextToSpeech.OnInitListener를 상속하는 과정에서 필요한 부분
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            // set US English as language for tts
            val result = tts!!.setLanguage(Locale.KOREAN)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language specified is not supported!")
            }
            isReady=true;
        } else {
            Log.e("TTS", "Initilization Failed!")
        }
    }

    // tts 음성을 출력하기 위한 함수
    fun speakOut(text_s : String) {
        tts!!.speak(text_s, TextToSpeech.QUEUE_FLUSH, null,"")
    }
}