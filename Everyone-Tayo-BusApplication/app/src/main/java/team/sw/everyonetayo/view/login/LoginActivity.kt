package team.sw.everyonetayo.view.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.login

import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.controller.login.LoginController
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.login.LoginRepository
import team.sw.everyonetayo.util.NetworkStatus
import team.sw.everyonetayo.util.PermissionCheck
import team.sw.everyonetayo.util.ToastWithSpeech
import team.sw.everyonetayo.view.BusDriver

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle("로그인")

        login.setOnClickListener{
            // 권한 체크
            if(!PermissionCheck.checkGps(applicationContext)){
                ToastWithSpeech.instance.toastShowWithNone("앱 권한 설정에서 GPS를 활성화 시켜주세요.")
            }else if(NetworkStatus.getConnectivityStatus(applicationContext) == NetworkStatus.TYPE_NOT_CONNECTED){
                ToastWithSpeech.instance.toastShowWithNone("인터넷 연결을 활성화 시켜주세요.")
            }else {
                //권한 체크 통과
                //토큰 확인 후 없다면 요청
                val loginController: LoginController = LoginContainer.instance.loginController()
                val loginRepository: LoginRepository = LoginContainer.instance.loginRepository()

                val id = username.text.toString()
                val pswd = password.text.toString()

                if(!loginRepository.isLogin()){
                    if(loginController.login(id, pswd) is Result.Success){
                        //권한체크, 로그인 통과
                        val intent = Intent(this, BusDriver::class.java)
                        startActivity(intent)
                    }else{
                        ToastWithSpeech.instance.toastShowWithNone("로그인에 실패했습니다")
                    }
                }
            }
        }

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