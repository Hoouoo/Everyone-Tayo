package team.sw.everyonetayo.view

import android.graphics.Color
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_bus_driver.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.ReservationContainer
import team.sw.everyonetayo.container.ViewContainer
import team.sw.everyonetayo.domain.ReservationDto
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.repository.heap.ReservationsRepository


class BusDriver : AppCompatActivity() {


    val items = mutableListOf<ListViewItem>()

    //test
    var tick:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_driver)

        //뷰 콘테이너에 추가
        ViewContainer.instance.add("BusDriver", this)


        //예약 리스트 자동 관리 시작
        ReservationContainer.instance.reservationManagement().start()


//        listView.setOnItemClickListener {
//                parent: AdapterView<*>, view: View, position: Int, id: Long ->
//            val item = parent.getItemAtPosition(position) as ListViewItem
//            Toast.makeText(this, item.people_num, Toast.LENGTH_SHORT).show()
//        }

        setTitle("운행 정보")
        /*
        logo.setOnClickListener {
            println("click")
            if(tick) {
                println("on")
                ReservationContainer.instance.reservationManagement().managementRunnable.testBusLatitude = 36.43535
                ReservationContainer.instance.reservationManagement().managementRunnable.testBusLongitude = 127.3863
                tick = false
            }else{
                println("off")
                ReservationContainer.instance.reservationManagement().managementRunnable.testBusLatitude = 34.0
                ReservationContainer.instance.reservationManagement().managementRunnable.testBusLongitude = 128.0
                tick = true
            }
        }
         */

        ride_test.setOnClickListener{
            testAddItem()
        }

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

        drop_test.setOnClickListener{
            lightOffOfRed()
        }
    }

    override fun onBackPressed() {
    }

    fun testAddItem(){
        val reservationsRepository: ReservationsRepository = ReservationContainer.instance.reservationsRepository()

        val reservationDto:ReservationDto = ReservationDto(
            "busStop",
            "36.43535",
            "127.3863",
            false,
            false
        )
        reservationsRepository.getReservationList().add(reservationDto)

        val viewResult: Result<Any> = ViewContainer.instance.get("BusDriver")

        if(viewResult is Result.Success){
            //busDriver 뷰 가져오기
            val busDriverView:BusDriver  = viewResult.data as BusDriver
            //TODO 예약들어올 때 액션 추가
            busDriverView.speakGreenBell()
            busDriverView.lightOnOfGreenBlink()
            busDriverView.additems("busStop")
        }
    }

    fun additems(busstop: String) {
        runOnUiThread(Runnable {
            if (listView.count == 0){
                items.add(ListViewItem(busstop))
            }else{
                for ( i in 0 until listView.count){
                    if(items.get(i).busstop.equals(busstop)){

                    }else{
                        items.add(ListViewItem(busstop))
                    }
                }
            }
            val adapter = ListViewAdapter(items)
            listView.adapter = adapter
        })
    }

    fun deleteitems(busstop: String) {
        runOnUiThread(Runnable {
            for (i in 0 until listView.count) {
                if (items.get(i).busstop.equals(busstop)) {
                    items.removeAt(i)
                    break
                }
            }
            val adapter = ListViewAdapter(items)
            listView.adapter = adapter
        })
    }

    fun speakGreenBell(){
        try{
            val notification = Uri.parse("android.resource://" + packageName + "/" + R.raw.bell2)
            val r1 : Ringtone = RingtoneManager.getRingtone(this, notification)
            r1.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun speekReadBell(){
        try{
            val notification = Uri.parse("android.resource://" + packageName + "/" + R.raw.bell)
            val r1 : Ringtone = RingtoneManager.getRingtone(this, notification)
            r1.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun lightOnOfGreen(){
        runOnUiThread {
            ride_test.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.color_ride_textview
                )
            )
        }
    }

    fun lightOnOfGreenBlink(){
        Thread(Runnable {
            for (i in 1..10){
                if(i % 2 == 1){
                    lightOnOfGreen()
                }else{
                    lightOffOfGreen()
                }
                Thread.sleep(333)
            }
        }).start()
    }

    fun lightOffOfGreen(){
        runOnUiThread {
            ride_test.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.custom_textview
                )
            )
        }
    }

    fun lightOnOfRed(){
        runOnUiThread {
            drop_test.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.color_drop_textview
                )
            )
        }
    }

    fun lightOffOfRed(){
        runOnUiThread {
            ride_test.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.custom_textview
                )
            )
        }
    }

    fun lightOnSky(){
        runOnUiThread {
            ride_test.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.color_arrive_textview
                )
            )
        }
    }

    fun lightOffOfSky(){
        runOnUiThread {
            ride_test.setBackgroundDrawable(
                ContextCompat.getDrawable(
                    this,
                    R.drawable.custom_textview
                )
            )
        }
    }

    override fun onDestroy() {
        //예약 리스트 자동 관리 종료
        ReservationContainer.instance.reservationManagement().stop()
        super.onDestroy()
    }

    fun listGreenBlink(converView:View):Thread{
        val tempThread:Thread = Thread(Runnable {
            for (i in 1..2){
                if(i % 2 == 1){
                    runOnUiThread{ converView.setBackgroundColor(Color.GREEN) }

                }else{
                    runOnUiThread{ converView.setBackgroundColor(Color.WHITE) }
                }
                Thread.sleep(333)
            }
        })
        tempThread.start()
        return tempThread
    }
}