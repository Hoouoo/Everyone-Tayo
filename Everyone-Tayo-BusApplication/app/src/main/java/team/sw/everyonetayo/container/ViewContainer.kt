package team.sw.everyonetayo.container

import android.support.v7.app.AppCompatActivity
import team.sw.everyonetayo.domain.Result
import java.lang.Exception

class ViewContainer {

    companion object {
        val instance: ViewContainer = ViewContainer()
    }

    private var appCompatActivityContext:HashMap<String,Any> = HashMap();

    fun add(name:String ,Object: Any):Boolean{
        try {
            if (appCompatActivityContext.containsKey(name)) {
                appCompatActivityContext.remove(name)
                appCompatActivityContext.put(name, Object)
            } else {
                appCompatActivityContext.put(name, Object)
            }
            return true
        }catch (e:Exception){
            return false;
        }
    }

    fun get(name:String):Result<Any>{
        try{
            if(appCompatActivityContext.containsKey(name)){
                return Result.Success(appCompatActivityContext.get(name)!!)
            }else{
                return Result.Error(Exception("Not found view"))
            }
        }catch (e:Exception){
            return Result.Error(e)
        }
    }
}