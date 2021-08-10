package team.sw.everyonetayo.container

import team.sw.everyonetayo.controller.stt.SttController
import team.sw.everyonetayo.model.stt.SttService
import team.sw.everyonetayo.repository.stt.SttRepository

class SttContainer {

    companion object{
        val instance:SttContainer = SttContainer()
    }

    private var sttController:SttController;
    private var sttService:SttService;
    private var sttRepository:SttRepository;

    constructor(){
        sttService = sttService()
        sttController = sttController()
        sttRepository=sttRepository()
    }

    fun sttRepository():SttRepository{
        if(sttRepository==null){
            sttRepository = SttRepository()
            return sttRepository
        }else{
            return sttRepository
        }
    }

    fun sttService():SttService{
        if(sttService==null){
            sttService = SttService(sttRepository())
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