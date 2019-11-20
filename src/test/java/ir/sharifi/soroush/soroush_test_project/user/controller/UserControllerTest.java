package ir.sharifi.soroush.soroush_test_project.user.controller;

import ir.sharifi.soroush.soroush_test_project.user.UserDbConfig;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.service.IUserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = {UserDbConfig.class})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Test
    void getAllUser() throws Exception {
        List<AppUser> toDoList = new ArrayList<AppUser>();
        toDoList.add( AppUser.builder().firstName("hossein").lastName("sharifi").userName("mhsharifi").password("mypassword").personnelNumber(123456).build()   );
        toDoList.add( AppUser.builder().firstName("sharif").lastName("hosseini").userName("shhosseini").password("mypassword").personnelNumber(123457).build()   );
        when(userService.getModels()).thenReturn(toDoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(jsonPath("$", hasSize(2))).andDo(print());
    }
}