package team.sw.everyonetayo.model.stt

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.SttContainer
import team.sw.everyonetayo.domain.WrappedString
import team.sw.everyonetayo.repository.stt.SttRepository
import team.sw.everyonetayo.util.NumberExtractor
import team.sw.everyonetayo.view.SelectService
import team.sw.everyonetayo.view.VoiceCheck


class SttService {

    var intent: Intent? = null
    var mRecognizer: SpeechRecognizer? = null
    var appCompatActivity: AppCompatActivity? = null
    var result: WrappedString = WrappedString("")
    val PERMISSION = 1
    var textView:TextView? = null;

    val sttRepository:SttRepository

    constructor(sttRepository: SttRepository) {
        this.sttRepository = sttRepository
    }

    fun settingStt(appCompatActivity: AppCompatActivity, result:WrappedString, textView:TextView){
        this.appCompatActivity = appCompatActivity
        this.result = result
        this.textView = textView

        if (Build.VERSION.SDK_INT >= 23) {
            // 퍼미션 체크
            ActivityCompat.requestPermissions(
                appCompatActivity, arrayOf(
                    Manifest.permission.INTERNET,
                    Manifest.permission.RECORD_AUDIO
                ), PERMISSION
            )
        }

        intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent!!.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, appCompatActivity!!.getPackageName())
        intent!!.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ko-KR")
    }

    fun getOnClickListener(): View.OnClickListener? {
        return View.OnClickListener {
            result.myString = ""
            mRecognizer = SpeechRecognizer.createSpeechRecognizer(appCompatActivity)
            mRecognizer!!.setRecognitionListener(listener)
            mRecognizer!!.startListening(intent)
        }
    }

    fun startRecod(){
        result.myString = ""
        mRecognizer = SpeechRecognizer.createSpeechRecognizer(appCompatActivity)
        mRecognizer!!.setRecognitionListener(listener)
        mRecognizer!!.startListening(intent)
    }

    private val listener: RecognitionListener = object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle) {
            Toast.makeText(
                appCompatActivity!!.getApplicationContext(),
                "음성인식을 시작합니다.",
                Toast.LENGTH_SHORT
            ).show()
        }

        override fun onBeginningOfSpeech() {}
        override fun onRmsChanged(rmsdB: Float) {}
        override fun onBufferReceived(buffer: ByteArray) {}
        override fun onEndOfSpeech() {}
        override fun onError(error: Int) {
            val message: String
            message = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "오디오 에러"
                SpeechRecognizer.ERROR_CLIENT -> "클라이언트 에러"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "퍼미션 없음"
                SpeechRecognizer.ERROR_NETWORK -> "네트워크 에러"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "네트웍 타임아웃"
                SpeechRecognizer.ERROR_NO_MATCH -> "찾을 수 없음"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RECOGNIZER가 바쁨"
                SpeechRecognizer.ERROR_SERVER -> "서버가 이상함"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "말하는 시간초과"
                else -> "알 수 없는 오류임"
            }
            Toast.makeText(
                appCompatActivity!!.getApplicationContext(),
                "에러가 발생하였습니다. : $message",
                Toast.LENGTH_SHORT
            ).show()
            result.myString=message
            textView!!.setText(result.myString)
        }

        override fun onResults(results: Bundle) {
            val matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
            for (i in matches!!.indices) {
                result.myString = matches[i]
            }

            // 레포지토리에 저장
            SttContainer.instance.sttRepository().recodeString = WrappedString(NumberExtractor.numberExtraction(result.myString))
            
            //다음 화면으로 이동
            val intent = Intent(appCompatActivity!!, VoiceCheck::class.java) 
            appCompatActivity!!.startActivity(intent)
        }

        override fun onPartialResults(partialResults: Bundle) {}
        override fun onEvent(eventType: Int, params: Bundle) {}
    }
}