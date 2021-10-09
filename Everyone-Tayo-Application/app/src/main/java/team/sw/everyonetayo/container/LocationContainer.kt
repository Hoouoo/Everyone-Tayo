package team.sw.everyonetayo.container

import team.sw.everyonetayo.controller.location.LocationController
import team.sw.everyonetayo.model.location.LocationService


class LocationContainer {
    companion object {
        val instance: LocationContainer = LocationContainer()
    }

    private var locationController: LocationController? = null;
    private var locationService: LocationService? = null;

    constructor(){
        locationController = locationController()
        locationService = locationService()
    }

    fun locationService():LocationService{
        if(locationService==null){
            locationService = LocationService()
            return locationService!!
        }else{
            return locationService!!
        }
    }

    fun locationController():LocationController{
        if(locationController==null){
            locationController = LocationController(locationService())
            return locationController!!
        }else{
            return locationController!!
        }
    }
}