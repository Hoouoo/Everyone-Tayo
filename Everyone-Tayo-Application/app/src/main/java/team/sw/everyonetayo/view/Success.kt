package team.sw.everyonetayo.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_select_service.home
import kotlinx.android.synthetic.main.activity_voice_check.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.container.ReservationContainer
import team.sw.everyonetayo.container.SttContainer
import team.sw.everyonetayo.controller.reservation.ReservationController
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.domain.ReservationResponse
import team.sw.everyonetayo.util.GpsTracker
import java.io.IOException
import java.lang.Exception

class Success : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)

        setTitle("예약 성공")


        home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        //뒤로가기 버튼
        supportActionBar?.setDisplayHomeAsUpEnabled(true);

        //예약을 위한 변수 정의
        val busNumber:String = SttContainer.instance.sttRepository().recodeString.myString
        val latitude:String = GpsTracker(this).latitude.toString()
        val longitude:String = GpsTracker(this).longitude.toString()
        val token:String = LoginContainer.instance.loginRepository().getLoggedInUser()!!.token;

        //예약
        val reservationController: ReservationController =
            ReservationContainer.instance.reservationController()

        val result:Result<ReservationResponse> = reservationController.reservation(busNumber, latitude, longitude, token)
        if(result is Result.Success){
            //예약 성공 텍스트
            val confirmStateOfBus:String = (
                    SttContainer.instance.sttRepository().recodeString.myString
                            + "번 버스 예약되었습니다."
                    )
            //예약 성공 텍스트 적용
            confirmTextView.setText(confirmStateOfBus)
        }else if(result is Result.Error){
            //예약 실패 텍스트
            val confirmStateOfBus:String = ("연결 또는 서버에 문제가 있습니다.")
            //예약 실패 텍스트 적용
            confirmTextView.setText(confirmStateOfBus)
        }
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