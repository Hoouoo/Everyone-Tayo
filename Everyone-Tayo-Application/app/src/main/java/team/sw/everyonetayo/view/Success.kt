package team.sw.everyonetayo.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_select_service.*
import kotlinx.android.synthetic.main.activity_select_service.home
import kotlinx.android.synthetic.main.activity_voice_check.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.ReservationContainer
import team.sw.everyonetayo.container.SttContainer
import team.sw.everyonetayo.controller.reservation.ReservationController
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.util.GpsTracker

class Success : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voice_check)

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

        //예약
        val reservationController:ReservationController = ReservationContainer.instance.reservationController()
        reservationController.reservation(busNumber, latitude, longitude)

        //예약 성공 텍스트
        val confirmStateOfBus:String = (
                SttContainer.instance.sttRepository().recodeString.myString
                        + "번 버스 예약되었습니다."
                )
        
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