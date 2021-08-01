package team.sw.everyonetayo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_choicebus.*
import kotlinx.android.synthetic.main.activity_selectservice.home

class ChoiceBus : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choicebus)

        setTitle("버스 종류 선택")


        home.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        regular_bus.setOnClickListener{
            val intent = Intent(this, VoiceReader::class.java)
            startActivity(intent)
        }


        village_bus.setOnClickListener{
            val intent = Intent(this, VoiceReader::class.java)
            startActivity(intent)
        }


        express_bus.setOnClickListener{
            val intent = Intent(this, VoiceReader::class.java)
            startActivity(intent)
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