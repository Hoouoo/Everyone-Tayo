package team.sw.everyonetayo.bus;

import lombok.Getter;

import java.util.UUID;

@Getter
public class BusDto {

    private UUID uuid;
    private String Token;
    private String busNumber;

    public BusDto(UUID uuid, String token, String busNumber) {
        this.uuid = uuid;
        Token = token;
        this.busNumber = busNumber;
    }

}

