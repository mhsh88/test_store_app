package ir.sharifi.soroush.soroush_test_project.user.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class LoginRequest implements Serializable {

    @NotNull(message = "login.username.notNull")
    private String username;

    @NotNull(message = "login.password.notNull")
    private String password;
}
