package team.sw.everyonetayo.bus;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BusDto {

    private String uuid;
    private String token;
    private String busNumber;

    public BusDto(String uuid, String token, String busNumber) {
        this.uuid = uuid;
        this.token = token;
        this.busNumber = busNumber;
    }

}

