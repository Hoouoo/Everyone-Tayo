package team.sw.everyonetayo.model.reservation

import retrofit2.Call
import team.sw.everyonetayo.domain.Reservation
import team.sw.everyonetayo.domain.Result
import team.sw.everyonetayo.http.HttpClient
import team.sw.everyonetayo.http.HttpService
import team.sw.everyonetayo.http.domain.ReservationResponse
import team.sw.everyonetayo.repository.reservation.ReservationRepository
import java.io.IOException
import java.lang.Exception
import java.net.SocketTimeoutException

class ReservationService {

    val reservationRepository:ReservationRepository;

    constructor(reservationRepository: ReservationRepository){
        this.reservationRepository = reservationRepository;
    }

    fun reservation(busNumber:String, latitude:String, longitude:String, token:String):Result<ReservationResponse>{
        val httpService:HttpService = HttpClient.getApiService();
        val postReservation: Call<ReservationResponse> = httpService.reservation(busNumber, latitude, longitude, token);

        try {
            var result:Result<ReservationResponse>? = null
            val thread:Thread = Thread(Runnable {
                try {
                    val reservationResponse: ReservationResponse? =
                        postReservation.execute().body();

                    if (reservationResponse == null) {
                        throw Exception();
                    }

                    val busNumber = busNumber;
                    val busStop = reservationResponse.busStop;
                    val timeStamp = reservationResponse.timeStamp;

                    reservationRepository.updateReservation(busNumber, busStop, timeStamp)
                    result = Result.Success(reservationResponse)
                }catch (e:SocketTimeoutException){
                    result = Result.Error(e)
                }catch(e:Exception){
                    result = Result.Error(e)
                }
            })

            thread.start() //통신시작
            thread.join() //통신종료까지 대기

            return result!!
        }catch (e:IOException){
            return Result.Error(e);
        }catch (e:Throwable){
            return Result.Error(IOException("Error reservation", e));
        }

    }
}