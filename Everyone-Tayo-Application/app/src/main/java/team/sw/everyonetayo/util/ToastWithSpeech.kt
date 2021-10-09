package team.sw.everyonetayo.util

import android.os.Handler
import android.os.Looper
import android.widget.Toast


class ToastWithSpeech {

    companion object {
        val instance:ToastWithSpeech = ToastWithSpeech()
    }

    fun toastShowWithSpeach (out:String) {
        Handler().postDelayed ({
            Toast.makeText(ApplicationContext.context(), out, Toast.LENGTH_SHORT).show()
        }, 0)

        if (TtsSpeaker.instance.isReady) {
            TtsSpeaker.instance.speakOut(out)
        }
    }
}