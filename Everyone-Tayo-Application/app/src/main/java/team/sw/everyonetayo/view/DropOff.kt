package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBar
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_dropoff.*
import kotlinx.android.synthetic.main.activity_dropoff.noButton
import kotlinx.android.synthetic.main.activity_dropoff.yesButton
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_voice_check.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.GetOffContainer
import team.sw.everyonetayo.controller.getoff.GetOffController
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.util.ToastWithSpeech
import team.sw.everyonetayo.util.TtsSpeaker

class DropOff : AppCompatActivity() {

    var getOffController:GetOffController? = null
    val DURATION:Long = 1000 //액티비티 이동의 1.5초 딜레이

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dropoff)

        getOffController = GetOffContainer.instance.getOffController()

        setTitle("하차 서비스")

        TtsSpeaker.instance.speakOut("이번 정류장에서 하차 하십니까? 위쪽 버튼은 예, 아래쪽 버튼은 아니요")


//        home.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        yesButton.setOnClickListener {
            if(getOffController!!.getOff() is Result.Success){
                ToastWithSpeech.instance.toastShowWithSpeach("하차 신청이 완료되었습니다.")
                val intent = Intent(this, ProcessEnd::class.java)
                startActivity(intent)
            }else {
                ToastWithSpeech.instance.toastShowWithSpeach("하차 신청이 되지 않았습니다.")
            }
        }

        noButton.setOnClickListener{
            Handler().postDelayed({
                onBackPressed()
            }, DURATION)
        }

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()


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
}