package team.sw.everyonetayo.controller.login

import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.model.login.LoginService;

class LoginController {
    val loginService:LoginService;

    constructor(loginService: LoginService){
        this.loginService = loginService;
    }

    fun login():Result<LoggedInUser> {
        return loginService.login();
    }
}