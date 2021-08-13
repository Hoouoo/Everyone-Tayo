package team.sw.everyonetayo.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.controller.login.LoginController
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.login.LoginRepository
import team.sw.everyonetayo.util.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TtsSpeaker.instance.setActivity(this)

        start_button.setOnClickListener{

            // 권한 체크
            if(!PermissionCheck.checkGps(applicationContext)){
                Toast.makeText(this.getApplicationContext(), "앱 권한 설정에서 Gps를 활성화 시켜주세요.", Toast.LENGTH_SHORT).show()
            }else if(!PermissionCheck.checkRecode(applicationContext)){
                Toast.makeText(this.getApplicationContext(), "앱 권한 설정에서 음성녹음을 활성화 시켜주세요.", Toast.LENGTH_SHORT).show()
            }else if(NetworkStatus.getConnectivityStatus(applicationContext) == NetworkStatus.TYPE_NOT_CONNECTED){
                Toast.makeText(this.getApplicationContext(), "인터넷 연결을 활성화 시켜주세요.", Toast.LENGTH_SHORT).show()
            }else {
                //권한 체크 통과
                //토큰 확인 후 없다면 요청
                val loginController:LoginController = LoginContainer.instance.loginController()
                val loginRepository:LoginRepository = LoginContainer.instance.loginRepository()
                if(!loginRepository.isLogin()){
                    if(loginController.login() is Result.Success){
                        //권한체크, 로그인 통과
                        val intent = Intent(this, VoiceReader::class.java)
                        startActivity(intent)
                    }else{
                        ToastWithSpeech.instance.toastShowWithSpeach("서버와 연결되지 않았습니다.")
                    }
                }
            }
        }


        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()
    }
}