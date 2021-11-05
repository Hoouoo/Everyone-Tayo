package team.sw.everyonetayo.controller.location

import team.sw.everyonetayo.container.LocationContainer
import team.sw.everyonetayo.model.location.LocationService
import team.sw.everyonetayo.util.ApplicationContext
import team.sw.everyonetayo.util.GpsTracker

class LocationController {

    private val locationService:LocationService

    constructor(locationService: LocationService){
        this.locationService = locationService
    }

    fun locationCheck(){
        val latitude:String = GpsTracker(ApplicationContext.context()).latitude.toString()
        val longitude:String = GpsTracker(ApplicationContext.context()).longitude.toString()

//        val latitude:String = "35.56977"
//        val longitude:String = "129.24821"
        locationService.locationCheck(latitude, longitude)
    }
}