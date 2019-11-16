package ir.sharifi.soroush.soroush_test_project.food.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseUpdateDto;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FoodUpdateDto extends BaseUpdateDto<Long> {
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