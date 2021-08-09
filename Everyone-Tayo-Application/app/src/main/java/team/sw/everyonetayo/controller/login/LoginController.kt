package team.sw.everyonetayo.controller.login

import team.sw.everyonetayo.model.login.LoginService;

class LoginController {
    val loginService:LoginService;

    constructor(loginService: LoginService){
        this.loginService = loginService;
    }

    fun login() {
        loginService.login();
    }
}