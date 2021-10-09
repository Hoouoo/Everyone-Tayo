package team.sw.everyonetayo.model.arrive

import android.view.View
import kotlinx.android.synthetic.main.custom_list_view.view.*
import team.sw.everyonetayo.container.ReservationContainer

class ArriveNoticeService {

    val reservationList = ReservationContainer.instance.reservationsRepository().getReservationList()
    var arriveNoticeThread:Thread? = null

    fun arriveNotice(convertView: View){
        for (reservationDto in reservationList) {
            if(convertView.busstop.text == reservationDto.busStop){
                if(reservationDto.isArrived && !reservationDto.isGone){
                    if(arriveNoticeThread==null){
                        arriveNoticeThread = Thread(ArriveNoticeRunnable(convertView))
                        arriveNoticeThread!!.start()
                    }
                }else if(reservationDto.isArrived && reservationDto.isGone){
                    if(arriveNoticeThread != null)
                        arriveNoticeThread!!.interrupt()
                        arriveNoticeThread = null
                }
            }
        }
    }
}