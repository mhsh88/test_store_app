package ir.sharifi.soroush.soroush_test_project.user.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInsertDto;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRegisterDto extends BaseInsertDto {

    @NotNull(message = "register.firstName.notNull")
    private String firstName;
    @NotNull(message = "register.lastName.notNull")
    private String lastName;
    @NotNull(message = "register.personnelNumber.notNull")
    private int personnelNumber;
    @NotNull(message = "register.username.notNull")
    private String userName;
    @NotNull(message = "register.password.notNull")
    private String password;
}
