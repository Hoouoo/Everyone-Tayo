package team.sw.everyonetayo.container

import team.sw.everyonetayo.controller.stt.SttController
import team.sw.everyonetayo.model.stt.SttService

class SttContainer {

    companion object{
        val instance:SttContainer = SttContainer()
    }

    private var sttController:SttController;
    private var sttService:SttService;

    constructor(){
        sttService = sttService()
        sttController = sttController()
    }

    fun sttService():SttService{
        if(sttService==null){
            sttService = SttService()
            return sttService
        }else{
            return sttService
        }
    }

    fun sttController():SttController{
        if(sttController==null){
            sttController = SttController(sttService())
            return sttController
        }else{
            return sttController
        }
    }

}