package team.sw.everyonetayo.util

import android.app.Application
import android.content.Context
import android.support.v7.app.AppCompatActivity

class ApplicationContext : Application(){

    init {
        instance = this
    }

    companion object {
        var instance: ApplicationContext? = null
        fun context(): Context {
            return instance!!.applicationContext
        }
    }
}