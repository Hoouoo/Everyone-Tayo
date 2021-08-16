package team.sw.everyonetayo.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.support.v7.app.ActionBar

import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.controller.login.LoginController
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.login.LoginRepository

import team.sw.everyonetayo.util.NetworkStatus
import team.sw.everyonetayo.util.PermissionCheck
import team.sw.everyonetayo.util.ToastWithSpeech
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        start_button.setOnClickListener{

            // 권한 체크
            if(!PermissionCheck.checkGps(applicationContext)){
                ToastWithSpeech.instance.toastShowWithSpeach("앱 권한 설정에서 GPS를 활성화 시켜주세요.")
            }else if(!PermissionCheck.checkRecode(applicationContext)){
                ToastWithSpeech.instance.toastShowWithSpeach("앱 권한 설정에서 음성녹음을 활성화 시켜주세요.")
            }else if(NetworkStatus.getConnectivityStatus(applicationContext) == NetworkStatus.TYPE_NOT_CONNECTED){
                ToastWithSpeech.instance.toastShowWithSpeach("인터넷 연결을 활성화 시켜주세요.")
            }else {
                //권한 체크 통과
                //토큰 확인 후 없다면 요청
                val loginController: LoginController = LoginContainer.instance.loginController()
                val loginRepository: LoginRepository = LoginContainer.instance.loginRepository()
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