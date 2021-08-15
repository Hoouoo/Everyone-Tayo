package team.sw.everyonetayo.view.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.login

import team.sw.everyonetayo.R
import team.sw.everyonetayo.view.BusDriver

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setTitle("로그인")

        login.setOnClickListener{
            val intent = Intent(this, BusDriver::class.java)
            startActivity(intent)
            finish()
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