package team.sw.everyonetayo.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_select_service.home
import kotlinx.android.synthetic.main.activity_success.*
import kotlinx.android.synthetic.main.activity_voice_check.*
import kotlinx.android.synthetic.main.activity_voice_check.confirmTextView
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.container.ReservationContainer
import team.sw.everyonetayo.container.SttContainer
import team.sw.everyonetayo.controller.reservation.ReservationController
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.domain.ReservationResponse
import team.sw.everyonetayo.util.ApplicationContext
import team.sw.everyonetayo.util.GpsTracker
import team.sw.everyonetayo.util.ToastWithSpeech
import team.sw.everyonetayo.util.TtsSpeaker
import java.io.IOException
import java.lang.Exception

class Success : AppCompatActivity() {

    var isGetOffButtonAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        setTitle("예약 결과")
        TtsSpeaker.instance.speakOut("예약중 입니다.")


//        home.setOnClickListener{
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }

        check2.setOnClickListener {
            val intent = Intent(this, DropOff::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        check2.visibility = Button.INVISIBLE

        val reservationThread: Thread = Thread(reservationRunnable())
        reservationThread.start()

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()
    }

    //뒤로가기 버튼 동작 코드
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    inner class reservationRunnable : Runnable {
        override fun run() {

            //예약을 위한 변수 정의
            val busNumber: String =
                SttContainer.instance.sttRepository().recodeString.myString
              val latitude:String = GpsTracker(ApplicationContext.context()).latitude.toString()
              val longitude:String = GpsTracker(ApplicationContext.context()).longitude.toString()
//            val latitude: String = "35.55521925976061"
//            val longitude: String = "129.26577127257917"
            val token: String =
                LoginContainer.instance.loginRepository().getLoggedInUser()!!.token;

            Log.d("reservation", "${busNumber}  ${latitude} ${longitude} ${token}")

            //예약
            val reservationController: ReservationController =
                ReservationContainer.instance.reservationController()

            val result: Result<ReservationResponse> =
                reservationController.reservation(busNumber, latitude, longitude, token)

            isGetOffButtonAvailable = true

            runOnUiThread { check2.visibility = Button.VISIBLE }

            if (result is Result.Success) {
                //예약 성공 텍스트
                val state: String = result.data.state

                if (state != "nope") {
                    val confirmStateOfBus: String = (
                            SttContainer.instance.sttRepository().recodeString.myString
                                    + "번 버스 예약되었습니다.\n" +
                                    "버스 도착까지 \n${state}분 남았습니다."
                            )
                    //예약 성공 텍스트 적용
                    TtsSpeaker.instance.speakOut(confirmStateOfBus+"\n 중앙에 하차버튼")
                    runOnUiThread { confirmTextView.setText(confirmStateOfBus) }

                } else {
                    val errorStatusOfBus: String = (
                            "현재 도착정보에 없는\n 버스입니다."
                            )
                    //예약 성공 텍스트 적용
                    TtsSpeaker.instance.speakOut(errorStatusOfBus)
                    runOnUiThread { confirmTextView.setText(errorStatusOfBus) }
                    check2.visibility = View.INVISIBLE;
                }

            } else if (result is Result.Error) {
                //예약 실패 텍스트
                val confirmStateOfBus: String = ("서버와 연결되지 않았습니다.")
                println(result.exception.message)
                //예약 실패 텍스트 적용
                TtsSpeaker.instance.speakOut(confirmStateOfBus)
                runOnUiThread { confirmTextView.setText(confirmStateOfBus) }
                check2.visibility = View.INVISIBLE;
            }
        }
    }
}