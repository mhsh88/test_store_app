package ir.sharifi.soroush.soroush_test_project.detergent.model;

import ir.sharifi.soroush.soroush_test_project.base.model.Product;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "detergent")
@DiscriminatorValue(value= "D")
@Data
@NoArgsConstructor





public class Detergent extends Product {

    @Builder(builderMethodName = "DetergentBuilder")
    public Detergent(String name, String producer, long isbn, LocalDateTime bringInDate, LocalDateTime bringOutDate, LocalDate productionDate, LocalDate expirationDate, double quantity, Unit unit) {
        super(name, producer, isbn, bringInDate, bringOutDate, productionDate, expirationDate, quantity, unit, ProductType.DETERGENT);
    }

    @Override
    public void setType(ProductType type) {
        super.setType(ProductType.DETERGENT);
    }
    @Override
    public ProductType getType() {
        return ProductType.DETERGENT;
    }
}
