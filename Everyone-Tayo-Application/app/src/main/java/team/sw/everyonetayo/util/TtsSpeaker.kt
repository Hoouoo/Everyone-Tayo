package team.sw.everyonetayo.util


import android.speech.tts.TextToSpeech
import android.support.v7.app.AppCompatActivity
import android.util.Log

import java.util.*

class TtsSpeaker {

    companion object {
        val instance:TtsSpeaker = TtsSpeaker()
    }

    private var tts: TextToSpeech? = null //tts 사용
    private var appCompatActivity:AppCompatActivity? = null
    private val detectDestroyThread:Thread

     constructor() {
         detectDestroyThread = Thread(DetectDestroy())
         detectDestroyThread.start()
    }

    // activity 설정을 위한 함수
    fun setActivity(activity: AppCompatActivity){
        appCompatActivity = activity
        tts = TextToSpeech(appCompatActivity, TextSpeechOnInitListner())
    }

    // tts 음성을 출력하기 위한 함수
    fun speakOut(speech : String) {
        if(!tts!!.isSpeaking) {
            tts!!.speak(speech, TextToSpeech.QUEUE_FLUSH, null, "")
        }
    }

    inner class TextSpeechOnInitListner : TextToSpeech.OnInitListener{
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
    }

    inner class DetectDestroy : Runnable{
        override fun run() {
            while (!detectDestroyThread.isInterrupted){
                if(appCompatActivity != null) {
                    if (appCompatActivity!!.isDestroyed) {
                        if (tts != null) {
                            tts!!.stop()
                            tts!!.shutdown()
                        }
                    }
                }
                Thread.sleep(500)
            }
        }
    }
}