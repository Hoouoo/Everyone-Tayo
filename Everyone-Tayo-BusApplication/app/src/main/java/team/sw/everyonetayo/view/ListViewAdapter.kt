package team.sw.everyonetayo.view

import android.graphics.Color
import android.graphics.Color.BLACK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import kotlinx.android.synthetic.main.custom_list_view.view.*
import team.sw.everyonetayo.R
import team.sw.everyonetayo.container.ArriveContainer
import team.sw.everyonetayo.model.arrive.ArriveNoticeService

class ListViewAdapter (private val items: MutableList<ListViewItem>): BaseAdapter()
{

    val arriveNoticeService:ArriveNoticeService = ArriveContainer.instance.arriveNoticeService()
    val arriveNoticeHashMap:HashMap<ListViewItem, Thread> = HashMap()

    override fun getCount(): Int = items.size
    override fun getItem(position: Int): ListViewItem = items[position]
    override fun getItemId(position: Int): Long = position.toLong()
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.custom_list_view, parent, false)
        val item: ListViewItem = items[position]
        convertView!!.busstop.text = item.busstop



        if(!arriveNoticeHashMap.containsKey(items[position])) {
            val arriveThread: Thread = Thread {
                while (!Thread.currentThread().isInterrupted) {
                    arriveNoticeService.arriveNotice(convertView)
                    try {
                        Thread.sleep(300)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                }
            }
            arriveThread.start()
            arriveNoticeHashMap.put(items[position], arriveThread)
        }

        return convertView
    }
}