package ir.sharifi.soroush.soroush_test_project.detergent.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseOutDto;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DetergentOutDto extends BaseOutDto<Long> {
    public DetergentOutDto() {
    }

    @Builder(builderMethodName = "DetergentOutDtoBuilder")
    public DetergentOutDto(Long id, String name, String producer, long isbn, LocalDateTime bringInDate, LocalDateTime bringOutDate, LocalDate productionDate, LocalDate expirationDate, double quantity, Unit unit) {


        super(id);
        this.name = name;
        this.producer = producer;
        this.isbn = isbn;
        this.bringInDate = bringInDate;
        this.bringOutDate = bringOutDate;
        this.productionDate = productionDate;
        this.expirationDate = expirationDate;
        this.quantity = quantity;
        this.unit = unit;
    }
    private String name;
    private String producer;
    private long isbn;
    private LocalDateTime bringInDate;
    private LocalDateTime bringOutDate;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private double quantity;
    private Unit unit;
    private ProductType type;
}
