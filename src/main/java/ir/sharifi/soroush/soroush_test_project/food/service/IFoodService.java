package ir.sharifi.soroush.soroush_test_project.food.service;

import ir.sharifi.soroush.soroush_test_project.base.service.BaseService;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodInDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodInsertDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodOutDto;
import ir.sharifi.soroush.soroush_test_project.food.dto.FoodUpdateDto;
import ir.sharifi.soroush.soroush_test_project.food.model.FoodStuff;
import ir.sharifi.soroush.soroush_test_project.user.dto.LoginRequest;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserLoginResponse;
import ir.sharifi.soroush.soroush_test_project.user.dto.UserRegisterDto;
import org.springframework.http.ResponseEntity;

public interface IFoodService extends BaseService<FoodStuff, Long, FoodInDto, FoodOutDto, FoodInsertDto, FoodUpdateDto> {

}
