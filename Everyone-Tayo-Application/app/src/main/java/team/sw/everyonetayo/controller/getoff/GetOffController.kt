package team.sw.everyonetayo.controller.getoff

import team.sw.everyonetayo.container.GetOffContainer
import team.sw.everyonetayo.domain.GetOffResponse
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.model.getoff.GetOffService

class GetOffController {

    val getOffService:GetOffService

    constructor(){
        getOffService = GetOffService()
    }

    fun getOff():Result<GetOffResponse>{
        return getOffService.getOff()
    }
}