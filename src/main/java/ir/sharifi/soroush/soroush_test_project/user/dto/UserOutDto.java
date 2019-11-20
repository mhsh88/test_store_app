package ir.sharifi.soroush.soroush_test_project.user.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseOutDto;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class UserOutDto extends BaseOutDto<Integer> {

    private String firstName;
    private String lastName;
    private int personnelNumber;
    private String userName;
    private String password;

    @Builder(builderMethodName = "UserOutDtoBuilder")
    public UserOutDto(Integer id, String firstName, String lastName, int personnelNumber, String userName, String password) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.personnelNumber = personnelNumber;
        this.userName = userName;
        this.password = password;
    }


}
