package ir.sharifi.soroush.soroush_test_project.user.service;

import ir.sharifi.soroush.soroush_test_project.user.dto.UserInsertDto;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserOutDto;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserUpdateDto;
import ir.sharifi.soroush.soroush_test_project.user.model.AppUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {
    @Autowired
    IUserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    private UserInsertDto initUser;
    private AppUser secondUser;
    private UserOutDto testUser;
    private ModelMapper modelMapper = new ModelMapper();
    private UserOutDto newUserOutDto;


    @BeforeEach
    void setUp() {

        String name = "notHossein";
        String lastName = "notLastName";
        String userName = "thisUserName";
        String password = passwordEncoder.encode("thisPassword");
        int personnelNum = 654321;

        initUser = UserInsertDto.builder().firstName(name).lastName(lastName).userName(userName).password(password).personnelNumber(personnelNum).build();
        secondUser = AppUser.builder().firstName(name).lastName(lastName).userName(userName).password(password).personnelNumber(personnelNum).build();

        testUser = userService.insert(initUser);



    }

    @AfterEach
    void tearDown() {
        userService.delete(testUser.getId());
        if(newUserOutDto!= null && newUserOutDto.getId()!=null) {
            userService.delete(newUserOutDto.getId());
        }
    }

    @Test
    void findById() {

        UserOutDto user = userService.findById(testUser.getId());
        assertNotNull(user);
        assertEquals(user,testUser);

    }

    @Test
    void insert() {
        String firstName = "abdcdge";

        String lastName = "tyukmnvbnm";
        int personnelNumber = 123543;
        String thisUserName = "thatUserName";
        String password = passwordEncoder.encode("");
        newUserOutDto = userService.insert(UserInsertDto.builder().firstName(firstName).lastName(lastName).personnelNumber(personnelNumber).userName(thisUserName).password(password).build());
        assertNotNull(newUserOutDto.getId());
        assertEquals(lastName,newUserOutDto.getLastName());
        assertEquals(firstName,newUserOutDto.getFirstName());
        assertEquals(thisUserName,newUserOutDto.getUserName());
        assertEquals(password,newUserOutDto.getPassword());
        assertEquals(personnelNumber,newUserOutDto.getPersonnelNumber());



        assertThrows(DataIntegrityViolationException.class, ()->userService.insert(initUser));


    }

    @Test
    void update() {
        String name = "notHossein";
        String lastName = "notLastName";
        String userName = "thisUserName";
        String password = passwordEncoder.encode("thisPassword");
        int personnelNum = 654321;
        testUser.setFirstName(name);
        testUser.setLastName(lastName);
        testUser.setUserName(userName);
        testUser.setPassword(password);
        testUser.setPersonnelNumber(personnelNum);
        testUser = userService.update(modelMapper.map(testUser, UserUpdateDto.class));
        assertEquals(testUser.getFirstName(),name);
        assertEquals(testUser.getLastName(),lastName);
        assertEquals(testUser.getUserName(),userName);
        assertEquals(testUser.getPassword(),password);
        assertEquals(testUser.getPersonnelNumber(),personnelNum);




    }

    @Test
    void delete() {
        userService.delete(testUser.getId());
        assertThrows(EntityNotFoundException.class,()->userService.findById(testUser.getId()));
    }

    @Test
    void getModels() {
        List<UserOutDto> models = userService.getModels();
        assertTrue(models.size()>0);
    }

    @Test
    void getRepository() {
        assertNotNull(userService.getRepository());
    }

    @Test
    void getUserByUserName() {
        assertNotNull(userService.getUserByUserName(testUser.getUserName()));
    }

}