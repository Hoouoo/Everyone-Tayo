package team.sw.everyonetayo.util

import android.widget.Toast

class ToastWithSpeech {

    companion object {
        val instance:ToastWithSpeech = ToastWithSpeech()
    }

    fun toastShowWithSpeach (out:String) {
        Toast.makeText(ApplicationContext.context(), out, Toast.LENGTH_SHORT).show()
        if (TtsSpeaker.instance.isReady) {
            TtsSpeaker.instance.speakOut(out)
        }
    }

    fun toastShowWithNone(out:String) {
        Toast.makeText(ApplicationContext.context(), out, Toast.LENGTH_SHORT).show()
    }
}