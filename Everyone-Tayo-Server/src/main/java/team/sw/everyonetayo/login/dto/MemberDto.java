package team.sw.everyonetayo.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberDto {

    private String username;
    private String password;
    private String tel;
    private String email;

    @Builder
    public MemberDto(String username, String password, String tel, String email) {
        this.username = username;
        this.password = password;
        this.tel = tel;
        this.email = email;
    }

}
