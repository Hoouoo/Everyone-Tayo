package team.sw.everyonetayo.model.location

import android.os.Looper
import retrofit2.Call
import team.sw.everyonetayo.domain.LoggedInUser
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.HttpService
import team.sw.everyonetayo.http.domain.LocationRequest
import team.sw.everyonetayo.http.domain.LocationResponse
import team.sw.everyonetayo.http.domain.LoginResponse
import team.sw.everyonetayo.util.Distance
import team.sw.everyonetayo.util.Distance.distance
import team.sw.everyonetayo.util.ToastWithSpeech
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

class LocationService {

    fun locationCheck(latitude:String, longitude:String) {
        val httpService: HttpService = HttpClient.getApiService();
        val postLocation: Call<LocationResponse> = httpService.location(LocationRequest(latitude, longitude))

        val thread: Thread = Thread(Runnable {
            try {
                Looper.prepare()
                val locationResponse: LocationResponse? = postLocation.execute().body()
                if(locationResponse != null) {
                    val busstopName: String = locationResponse!!.name
                    val busstopLatitude: Double = locationResponse!!.latitude.toDouble()
                    val busstopLongitude: Double = locationResponse!!.longitude.toDouble()

                    val latitudeD:Double = latitude.toDouble()
                    val longitudeD:Double = longitude.toDouble()

                    val distance:Double = Distance.distance(latitudeD, longitudeD, busstopLatitude, busstopLongitude, "meter")

                    ToastWithSpeech.instance.toastShowWithSpeach("가장 가까운 역은 $busstopName 이고, 거리는 $distance 미터")

                }else{
                    ToastWithSpeech.instance.toastShowWithSpeach("통신 실패")
                    throw Exception("통신 실패")
                }
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        })

        thread.start() // 통신시작
        thread.join()  // 통신종료까지 대기
    }
}