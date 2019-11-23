package ir.sharifi.soroush.soroush_test_project.user.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInsertDto;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDto extends BaseInsertDto {
    private String firstName;
    private String lastName;
    private int personnelNumber;
    private String userName;
    private String password;
}
