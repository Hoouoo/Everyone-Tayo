package team.sw.everyonetayo.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import team.sw.everyonetayo.R
import team.sw.everyonetayo.util.TtsSpeaker

class Loading : AppCompatActivity() {
    var loadingThread = Thread();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        loadingThread = Thread(Runnable {
            while (!loadingThread.isInterrupted){
                if(TtsSpeaker.instance.isReady){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                Thread.sleep(100)
            }
        })

        loadingThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingThread.interrupt()
    }
}