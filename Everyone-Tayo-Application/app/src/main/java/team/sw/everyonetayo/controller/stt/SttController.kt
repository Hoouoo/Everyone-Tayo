package team.sw.everyonetayo.controller.stt

import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import team.sw.everyonetayo.domain.WrappedString
import team.sw.everyonetayo.model.stt.SttService

class SttController {

    val sttService:SttService

    constructor(sttService: SttService){
        this.sttService = sttService
    }

    fun settingSst(appCompatActivity: AppCompatActivity, wrappedString: WrappedString, textView: TextView){
        sttService.settingStt(appCompatActivity, wrappedString, textView)
    }

    fun startRecod(){
        sttService.startRecod()
    }

    fun getOnClickListener(): View.OnClickListener? {
        return sttService.getOnClickListener();
    }
}