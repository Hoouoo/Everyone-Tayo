package team.sw.everyonetayo.util

import android.widget.Toast

class ToastWithSpeech {

    companion object {
        val instance:ToastWithSpeech = ToastWithSpeech()
    }

    fun toastShowWithSpeach (out:String) {
        Toast.makeText(ApplicationContext.context(), out, Toast.LENGTH_SHORT).show()
        TtsSpeaker.instance.speakOut(out)
    }

}