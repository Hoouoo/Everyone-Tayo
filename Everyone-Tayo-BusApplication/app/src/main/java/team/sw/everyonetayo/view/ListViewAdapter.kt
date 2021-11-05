package team.sw.everyonetayo.view

import android.graphics.Color
import android.graphics.Color.BLACK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.custom_list_view.view.*
import team.sw.everyonetayo.R

class ListViewAdapter (private val items: MutableList<ListViewItem>): BaseAdapter()
{
    val arriveNoticeHashMap:HashMap<ListViewItem, Thread> = HashMap()
    //val arriveNoticeService:ArriveNoticeService = ArriveContainer.instance.arriveNoticeService()

    override fun getCount(): Int = items.size
    override fun getItem(position: Int): ListViewItem = items[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.custom_list_view, parent, false)
        val item: ListViewItem = items[position]
        convertView!!.busstop.text = item.busstop

/*
            val arriveThread: Thread = Thread {
                while (!Thread.currentThread().isInterrupted) {
                    arriveNoticeService.arriveNotice(convertView)
                    try {
                        Thread.sleep(100)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            arriveThread.start()
            if(arriveNoticeHashMap.containsKey(items[position])){
                arriveNoticeHashMap.remove(items[position])
                arriveNoticeHashMap.put(items[position], arriveThread)
            }else{
                arriveNoticeHashMap.put(items[position], arriveThread)
            }
 */
        return convertView
    }
/*
    class ArriveRunnable : Runnable{
        val arriveNoticeService:ArriveNoticeService = ArriveContainer.instance.arriveNoticeService()
        val convertView:View
        constructor(convertView:View, arriveThread:Thread){
            this.convertView = convertView
        }

        override fun run() {
            while (!Thread.currentThread().isInterrupted) {
                arriveNoticeService.arriveNotice(convertView, Thread.currentThread())
                try {
                    Thread.sleep(300)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
 */
}