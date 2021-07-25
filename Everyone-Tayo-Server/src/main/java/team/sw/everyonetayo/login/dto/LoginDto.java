package team.sw.everyonetayo.login.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter
public class LoginDto {

    @NotEmpty
    @Size(min= 2, max= 10)
    private String username;

    @NotEmpty
    @Size(min=4, max=20)
    private String password;


}
