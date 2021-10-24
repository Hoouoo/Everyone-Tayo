package team.sw.everyonetayo.model.arrive

import android.graphics.Color
import android.view.View
import team.sw.everyonetayo.container.ViewContainer
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.view.BusDriver

class ArriveNoticeRunnable : Runnable {

    val convertView:View
    val viewResult: Result<Any> = ViewContainer.instance.get("BusDriver")
    var busDriverView:BusDriver? = null

    constructor(convertView:View){
        this.convertView = convertView
    }

    override fun run() {
        if(viewResult is Result.Success){
            //busDriver 뷰 가져오기
            busDriverView = viewResult.data as BusDriver
        }
        while (!Thread.interrupted()){
            val blinkThread:Thread = busDriverView!!.listGreenBlink(convertView)
            blinkThread.join()
        }
    }
}