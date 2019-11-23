package ir.sharifi.soroush.soroush_test_project.user.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseUpdateDto;
import lombok.Data;

@Data
public class UserUpdateDto extends BaseUpdateDto<Integer> {
    private String firstName;
    private String lastName;
    private int personnelNumber;
    private String userName;
    private String password;
}
