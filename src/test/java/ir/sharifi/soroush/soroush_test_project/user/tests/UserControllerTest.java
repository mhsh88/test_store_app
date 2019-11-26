package ir.sharifi.soroush.soroush_test_project.user.tests;

import ir.sharifi.soroush.soroush_test_project.H2TestProfileJPAConfig;
import ir.sharifi.soroush.soroush_test_project.TestConfigs;
import ir.sharifi.soroush.soroush_test_project.user.controller.UserController;
import ir.sharifi.soroush.soroush_test_project.user.dto.LoginRequest;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserLoginResponse;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserOutDto;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserRegisterDto;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import ir.sharifi.soroush.soroush_test_project.user.repo.UserRepository;
import ir.sharifi.soroush.soroush_test_project.user.service.IUserService;
import ir.sharifi.soroush.soroush_test_project.user.service.UserServiceImpl;
import ir.sharifi.soroush.soroush_test_project.utils.jwtUtils.TokenProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = {
        H2TestProfileJPAConfig.class,
        UserController.class,
        IUserService.class,
        UserServiceImpl.class,
        TestConfigs.class
})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PasswordEncoder encoder;

    @MockBean
    AuthenticationManager authenticationManager;

    @MockBean
    TokenProvider tokenProvider;

    @Autowired
    UserController userController;
    @Autowired
    UserRepository userRepository;
    private AppUser initUser;
    private String name = "notHossein";
    private String lastName = "notLastName";
    private String userName = "controllerThisUserName";
    private String password = "thisPassword";
    private int personnelNum = 654321;
    private UserRegisterDto newRegistery;


    @BeforeEach
    void setUp() {




        initUser = AppUser.builder().firstName(name).lastName(lastName).userName(userName).password(encoder.encode(password)).personnelNumber(personnelNum).build();


        initUser = userRepository.save(initUser);


    }

    @AfterEach
    void tearDown() {
        userRepository.delete(initUser);
        if(newRegistery != null){
            userRepository.delete(userRepository.findByUserName(newRegistery.getUserName()));
        }
    }

    @Test
    void loadById() {
        ResponseEntity responseEntity = userController.loadById(initUser.getId());
        assertEquals(initUser.getFirstName(),((UserOutDto) responseEntity.getBody()).getFirstName());
        assertEquals(initUser.getLastName(),((UserOutDto) responseEntity.getBody()).getLastName());
        assertEquals(initUser.getPersonnelNumber(),((UserOutDto) responseEntity.getBody()).getPersonnelNumber());
        assertEquals(initUser.getId(),((UserOutDto) responseEntity.getBody()).getId());
        assertEquals(initUser.getUserName(),((UserOutDto) responseEntity.getBody()).getUserName());

        assertThrows(InvalidDataAccessApiUsageException.class,()->userController.loadById(null));


    }

    @Test
    void getAll() {
        ResponseEntity all = userController.getAll();
        assertTrue(((List) all.getBody()).size()>0);
        assertEquals(name, ((List<UserOutDto>) all.getBody()).get(0).getFirstName());
        assertEquals(lastName, ((List<UserOutDto>) all.getBody()).get(0).getLastName());
        assertEquals(personnelNum, ((List<UserOutDto>) all.getBody()).get(0).getPersonnelNumber());
        assertEquals(userName, ((List<UserOutDto>) all.getBody()).get(0).getUserName());
        assertEquals(initUser.getId(), ((List<UserOutDto>) all.getBody()).get(0).getId());
    }

    @Test
    void getService() {
        assertNotNull(userController.getService());

    }

    @Test
    void login() {
        Mockito.when(tokenProvider.createToken(Mockito.any(String.class))).thenReturn("qwertyuiop");
        LoginRequest loginRequest = LoginRequest.builder().username(userName).password(password).build();
        ResponseEntity loginResponse = userController.login(loginRequest);
        assertEquals(loginResponse.getStatusCodeValue(),200);
        assertNotNull(((UserLoginResponse)loginResponse.getBody()).getToken());


        String userName = this.userName;
        String encode = encoder.encode(password);

        Mockito.when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new UsernameNotFoundException("exception"));
        ResponseEntity fakeLogin = userController.login(LoginRequest.builder().username(userName).password(encode).build());

        assertEquals(fakeLogin.getBody(),"username.not.found");
        assertEquals(400,fakeLogin.getStatusCodeValue());


    }

    @Test
    void userRegister() {

        UserRegisterDto registerDto = UserRegisterDto.builder().firstName(name).lastName(lastName).password(password).personnelNumber(personnelNum).userName(userName).build();
        assertThrows(DataIntegrityViolationException.class,()->userController.userRegister(registerDto));

        String newFirstName = "newFirstName";
        String newLastName = "newLastName";
        String newPassword = "newPassword";
        int personnelNumber = 987456;
        String newUsername = "newUsername";
        newRegistery = UserRegisterDto.builder().firstName(newFirstName).lastName(newLastName).password(newPassword).personnelNumber(personnelNumber).userName(newUsername).build();


        ResponseEntity responseEntity = userController.userRegister(newRegistery);
        assertNotNull(responseEntity.getBody());
        assertEquals(responseEntity.getBody().getClass(), UserOutDto.class);
        assertEquals(responseEntity.getStatusCode().value(),200);
        assertNotEquals(((UserOutDto)responseEntity.getBody()).getPassword(),newPassword);
        assertEquals(((UserOutDto)responseEntity.getBody()).getUserName(),newUsername);
        assertEquals(((UserOutDto)responseEntity.getBody()).getFirstName(),newFirstName);
        assertEquals(((UserOutDto)responseEntity.getBody()).getLastName(),newLastName);
        assertEquals(((UserOutDto)responseEntity.getBody()).getPersonnelNumber(),personnelNumber);






    }
}