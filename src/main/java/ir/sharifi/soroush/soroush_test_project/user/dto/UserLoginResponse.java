package ir.sharifi.soroush.soroush_test_project.user.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginResponse extends UserOutDto {

    @Builder(builderMethodName = "UserLoginResponseBuilder")
    public UserLoginResponse(Integer id,String firstName, String lastName, int personnelNumber, String userName, String password, String token) {
        super(id,firstName, lastName, personnelNumber, userName, password);
        this.token = token;
    }

    private String token;


}
