package team.sw.everyonetayo.container

import team.sw.everyonetayo.controller.getoff.GetOffController
import team.sw.everyonetayo.model.getoff.GetOffService

class GetOffContainer {

    companion object {
        val instance: GetOffContainer = GetOffContainer()
    }

    private var getOffController: GetOffController? = null
    private var getOffService: GetOffService? = null


    constructor(){
        getOffController = getOffController()
        getOffService = getOffService()
    }

    fun getOffController():GetOffController{
        if(getOffController==null)
                getOffController = GetOffController()
        return getOffController!!
    }

    fun getOffService():GetOffService{
        if(getOffService==null)
            getOffService = GetOffService()
        return getOffService!!
    }
}