package team.sw.everyonetayo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_selectservice.*
import team.sw.everyonetayo.ui.login.LoginActivity

class SelectService : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selectservice)

        setTitle("서비스 선택")


        riding2.setOnClickListener{
            val intent = Intent(this, ChoiceBus::class.java)
            startActivity(intent)
            finish()
        }

        stopover.setOnClickListener{
            val intent = Intent(this, DropOff::class.java)
            startActivity(intent)
        }

        home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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