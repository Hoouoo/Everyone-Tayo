package team.sw.everyonetayo.container

import team.sw.everyonetayo.controller.login.LoginController
import team.sw.everyonetayo.model.login.LoginService
import team.sw.everyonetayo.repository.login.LoginRepository
import team.sw.everyonetayo.repository.login.MemoryLoginRepository

class LoginContainer {

    companion object {
        val instance: LoginContainer = LoginContainer();
    }

    private var loginController:LoginController? = null;
    private var loginService:LoginService? = null;
    private var loginRepository:LoginRepository? = null;

    constructor(){
        loginController = loginController();
        loginService = loginService();
        loginRepository = loginRepository();
    }

    fun loginRepository():LoginRepository{
        if(loginRepository==null){
            loginRepository = MemoryLoginRepository();
            return loginRepository!!
        }else{
            return loginRepository!!
        }
    }

    fun loginService():LoginService{
        if(loginService==null){
            loginService = LoginService(loginRepository());
            return loginService!!;
        }else{
            return loginService!!;
        }
    }

    fun loginController():LoginController{
        if(loginController==null){
            loginController = LoginController(loginService());
            return loginController!!;
        }else{
            return loginController!!;
        }
    }
}