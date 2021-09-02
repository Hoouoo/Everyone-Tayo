package team.sw.everyonetayo.model.reservation

import android.location.Location
import team.sw.everyonetayo.container.ViewContainer
import team.sw.everyonetayo.domain.ReservationDto
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.heap.ReservationsRepository
import team.sw.everyonetayo.util.ApplicationContext
import team.sw.everyonetayo.util.Distance
import team.sw.everyonetayo.util.GpsTracker
import team.sw.everyonetayo.view.BusDriver

class ManagementRunnable : Runnable {

    private val reservationsRepository:ReservationsRepository

    constructor(reservationsRepository: ReservationsRepository){
        this.reservationsRepository = reservationsRepository
    }

    override fun run() {
        val reservationList = reservationsRepository.getReservationList()
        val gpsTracker:GpsTracker = GpsTracker(ApplicationContext.context())
        
        // 버스 뷰 불러오기
        val viewResult:Result<Any> = ViewContainer.instance.get("BusDriver")
        var busDriver:BusDriver? = null
        if(viewResult is Result.Success){
            busDriver = viewResult.data as BusDriver
        }

        while(!Thread.interrupted()){
            val busLatitude:Double = GpsTracker(ApplicationContext.context()).latitude
            val busLongitude:Double = GpsTracker(ApplicationContext.context()).longitude
            
            // 삭제 예정 리스트
            val removed:ArrayList<ReservationDto> = ArrayList()
            
            // 거리 계산으로 삭제 예정 리스트에 추가하는 코드
            for (reservationDto in reservationList) {
                val latitude:Double =  reservationDto.latitude.toDouble()
                val longitude:Double = reservationDto.longitude.toDouble()

                val distance = Distance.distance(
                    busLatitude,
                    busLongitude,
                    latitude,
                    longitude,
                    "meter"
                )

                if(!reservationDto.isArrived){
                    if(distance<=6){
                        reservationDto.isArrived = true
                    }
                }else{
                    if(distance>6){
                        reservationDto.isGone = true
                        removed.add(reservationDto)
                    }
                }
            }

            //삭제 예정 대기를 삭제
            for (reservationDto in removed) {
                busDriver!!.deleteitems(reservationDto.busStop)
                reservationList.remove(reservationDto)
            }
        }
    }
}