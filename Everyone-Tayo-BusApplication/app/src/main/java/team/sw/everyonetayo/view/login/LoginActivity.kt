package team.sw.everyonetayo.view.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.login

import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.controller.login.LoginController
import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.repository.login.LoginRepository
import team.sw.everyonetayo.util.ApplicationContext
import team.sw.everyonetayo.view.BusDriver
import javax.security.auth.login.LoginException

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle("로그인")

        login.setOnClickListener{
            val loginController: LoginController = LoginContainer.instance.loginController()
            val loginRepository: LoginRepository = LoginContainer.instance.loginRepository()
            if(!loginRepository.isLogin()){
                val loginResult:Result<LoggedInUser> = loginController.login(username.text.toString(), password.text.toString())
                if(loginResult is Result.Success) {
                    //권한체크, 로그인 통과
                    Log.d("ServerTest", loginRepository.getLoggedInUser()!!.token)
                    val intent = Intent(this, BusDriver::class.java)
                    startActivity(intent)
                    finish()
                }else if(loginResult is Result.Error){
                    if(loginResult.exception is LoginException){
                        Toast.makeText(this.applicationContext, "로그인 실패", Toast.LENGTH_SHORT)
                    }else{
                        Toast.makeText(this.applicationContext, "서버와 연결되지 않았습니다.", Toast.LENGTH_SHORT)
                    }
                }
            }else{
                val intent = Intent(this, BusDriver::class.java)
                startActivity(intent)
                finish()
            }
        } //로그인 버튼을 눌렸을 때




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