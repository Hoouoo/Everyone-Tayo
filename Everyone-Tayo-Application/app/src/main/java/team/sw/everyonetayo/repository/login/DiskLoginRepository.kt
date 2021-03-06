package team.sw.everyonetayo.repository.login

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.util.ApplicationContext

class DiskLoginRepository : LoginRepository {
    val sp:SharedPreferences;
    val editor:SharedPreferences.Editor;

    constructor(){
        sp =  ApplicationContext.context().getSharedPreferences("loggedInUser", Context.MODE_PRIVATE);
        editor = sp.edit()
    }

    override fun login(loggedInUser: LoggedInUser): Result<LoggedInUser> {
        editor.putString("token", loggedInUser.token)
        editor.commit()
        return Result.Success(loggedInUser)
    }

    override fun logout() {
        editor.remove("token")
        editor.commit()
    }

    override fun getLoggedInUser(): LoggedInUser? {
        return LoggedInUser(sp.getString("token", "default")!!)
    }

    override fun isLogin(): Boolean {
        val token:String = sp.getString("token", "default")!!
        return !token.equals("default")
    }

}