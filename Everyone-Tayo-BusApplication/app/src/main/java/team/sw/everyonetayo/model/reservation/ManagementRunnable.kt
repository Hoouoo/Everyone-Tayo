package team.sw.everyonetayo.model.reservation

import android.widget.Adapter
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_bus_driver.*
import team.sw.everyonetayo.container.LoginContainer
import team.sw.everyonetayo.container.ViewContainer
import team.sw.everyonetayo.domain.ReservationDto
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.heap.ReservationsRepository
import team.sw.everyonetayo.repository.login.LoginRepository
import team.sw.everyonetayo.util.ApplicationContext
import team.sw.everyonetayo.util.Distance
import team.sw.everyonetayo.util.GpsTracker
import team.sw.everyonetayo.util.TtsSpeaker
import team.sw.everyonetayo.view.BusDriver
import team.sw.everyonetayo.view.ListViewAdapter

class ManagementRunnable : Runnable {

    private val reservationsRepository:ReservationsRepository
    private val errorRange:Int = 10
    private val correctRange:Int = 3
    private var announcementThread:Thread? = null
    private var blinkThread:Thread? = null

    var testBusLatitude:Double = 0.0
    var testBusLongitude:Double = 0.0
    var speakable:Boolean = false
    var blinkable:Boolean = false

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

            // 위도 경도 받기 (버스)
            var busLatitude:Double = GpsTracker(ApplicationContext.context()).latitude
            var busLongitude:Double = GpsTracker(ApplicationContext.context()).longitude

//            var busLatitude:Double = testBusLatitude
//            var busLongitude:Double = testBusLongitude


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
                    if(distance<=correctRange + errorRange){
                        reservationDto.isArrived = true
                        speakable=true
                        blinkable=true

                        // 안내 소리 발생 스레드 시작
                        announcementThread = Thread(AnnouncementRunnable())
                        announcementThread!!.start()

                        blinkThread =Thread(BlinkRunnable())
                        blinkThread!!.start()
                    }
                }else{
                    if(distance>correctRange + errorRange){
                        reservationDto.isGone = true
                        
                        //안내 소리 발생 스레드 정지 및 삭제
                        if(announcementThread!=null) {
                            speakable=false
                            announcementThread!!.interrupt()
                            announcementThread = null
                        }

                        if(blinkThread!=null){
                            blinkThread!!.interrupt()
                            blinkable = false
                            blinkThread = null
                        }

                        removed.add(reservationDto)
                    }
                }
            }

            //삭제 예정 대기를 삭제
            for (reservationDto in removed) {
                busDriver!!.deleteitems(reservationDto.busStop)
                reservationList.remove(reservationDto)
            }

            try {
                Thread.sleep(300)
            }catch (e:InterruptedException){
                e.printStackTrace()
            }
        }
    }

    inner class AnnouncementRunnable:Runnable{

        val repository:LoginRepository = LoginContainer.instance.loginRepository()
        val busnum:String
        val announcementComment:String

        constructor(){
            if(repository.isLogin()) {
                this.busnum = LoginContainer.instance.loginRepository().getLoggedInUser()!!.displayName
            }else{
                this.busnum = "-1"
            }

            announcementComment = "${busnum}번 버스"
        }

        override fun run() {
            while (speakable){
                println("speak")
                TtsSpeaker.instance.speakOut(announcementComment)
                try {
                    Thread.sleep(3000)
                }catch (e:InterruptedException){
                    //e.printStackTrace()
                }
            }
        }
    }

    inner class BlinkRunnable:Runnable {
        override fun run() {
            // 버스 뷰 불러오기
            val viewResult:Result<Any> = ViewContainer.instance.get("BusDriver")
            var busDriver:BusDriver? = null
            if(viewResult is Result.Success){
                busDriver = viewResult.data as BusDriver
            }

            while(blinkable){
                try {
                    busDriver!!.lightOnSky()
                    Thread.sleep(300)
                    busDriver!!.lightOffOfSky()
                    Thread.sleep(300)
                }catch (e:InterruptedException){
                    busDriver!!.lightOffOfSky()
                }
            }
        }
    }
}