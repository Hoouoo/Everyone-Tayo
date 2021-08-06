package team.sw.everyonetayo.http;

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import team.sw.everyonetayo.configuration.Config

public class HttpClient {
    companion object{
        fun getApiService():HttpService{
            return getInstance().create(HttpService::class.java)
        }

        fun getInstance():Retrofit {
            val gson:Gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(Config.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}
