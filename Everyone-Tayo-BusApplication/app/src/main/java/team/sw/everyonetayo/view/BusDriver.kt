package team.sw.everyonetayo.view

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


class BusDriver : AppCompatActivity() {

    var check: Boolean = false

    val items = mutableListOf<ListViewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bus_driver)
        
        //뷰 콘테이너에 추가
        ViewContainer.instance.add("BusDriver",this)

        //예약 리스트 자동 관리 시작
        ReservationContainer.instance.reservationManagement().start()


        listView.setOnItemClickListener {
                parent: AdapterView<*>, view: View, position: Int, id: Long ->
            val item = parent.getItemAtPosition(position) as ListViewItem
            Toast.makeText(this, item.people_num, Toast.LENGTH_SHORT).show()
        }

        setTitle("운행 정보")

        var actionBar : ActionBar?
        actionBar = supportActionBar
        actionBar?.hide()

    }


    fun additems(busstop: String, people_num: Int) {
        if (listView.count == 0){
            items.add(ListViewItem(busstop, people_num))
        }else{
            for ( i in 0 until listView.count){
                if(items.get(i).busstop.equals(busstop)){
                    items.get(i).people_num += 1
                    check = true
                    break
                }
            }
            if(!check) {
                items.add(ListViewItem(busstop, people_num))
            }
            check = false
        }
        val adapter = ListViewAdapter(items)
        listView.adapter = adapter
    }

    fun deleteitems(busstop: String) {
        for ( i in 0 until listView.count){
            if(items.get(i).busstop.equals(busstop)){

                if(items.get(i).people_num >= 1) {
                    items.removeAt(i)
                    break
                }
//                else{
//                    items.get(i).people_num -= 1
//                    break
//                }
            }
        }
        val adapter = ListViewAdapter(items)
        listView.adapter = adapter
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
        ride_test.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.color_ride_textview))
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
        ride_test.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_textview))
    }

    fun lightOnOfRed(){
        drop_test.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.color_drop_textview))
    }

    fun lightOffOfRed(){
        ride_test.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.custom_textview))
    }

    override fun onDestroy() {
        //예약 리스트 자동 관리 종료
        ReservationContainer.instance.reservationManagement().stop()
        super.onDestroy()
    }
}