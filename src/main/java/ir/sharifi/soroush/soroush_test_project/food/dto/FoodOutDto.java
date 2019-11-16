package ir.sharifi.soroush.soroush_test_project.food.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseOutDto;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodOutDto extends BaseOutDto<Long> {
    private String name;
    private String producer;
    private long isbn;
    private LocalDateTime bringInDate;
    private LocalDateTime bringOutDate;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private double quantity;
    private Unit unit;
}
