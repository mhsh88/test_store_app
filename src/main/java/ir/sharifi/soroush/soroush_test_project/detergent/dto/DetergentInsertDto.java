package ir.sharifi.soroush.soroush_test_project.detergent.dto;

import ir.sharifi.soroush.soroush_test_project.base.dto.BaseInsertDto;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class DetergentInsertDto extends BaseInsertDto {
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

    public ProductType getType() {
        return ProductType.DETERGENT;
    }

    public void setType(ProductType type) {
        this.type = ProductType.DETERGENT;
    }
}
