package team.sw.everyonetayo.view

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_process_end.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.util.ToastWithSpeech
import team.sw.everyonetayo.util.TtsSpeaker

class ProcessEnd : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_process_end)

        TtsSpeaker.instance.speakOut("모든 과정 완료, 중앙에 종료하기 버튼")

        exit_button.setOnClickListener {
            moveTaskToBack(true); // 태스크를 백그라운드로 이동
            finishAndRemoveTask(); // 액티비티 종료 + 태스크 리스트에서 지우기
            android.os.Process.killProcess(android.os.Process.myPid()); // 앱 프로세스 종료
        }


        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()
    }
}