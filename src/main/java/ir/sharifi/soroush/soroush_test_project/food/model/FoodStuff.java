package ir.sharifi.soroush.soroush_test_project.food.model;

import ir.sharifi.soroush.soroush_test_project.base.model.Product;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import ir.sharifi.soroush.soroush_test_project.base.model.Unit;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
@DiscriminatorValue(value= "F")
@Data
@NoArgsConstructor
public
class FoodStuff extends Product {

    @Builder(builderMethodName = "FoodBuilder")
    public FoodStuff(String name, String producer, long isbn, LocalDateTime bringInDate, LocalDateTime bringOutDate, LocalDate productionDate, LocalDate expirationDate, double quantity, Unit unit) {
        super(name, producer, isbn, bringInDate, bringOutDate, productionDate, expirationDate, quantity, unit, ProductType.FOODSTUFF);
    }
    @Override
    public void setType(ProductType type) {
        super.setType(ProductType.FOODSTUFF);
    }

    @Override
    public ProductType getType() {
        return ProductType.FOODSTUFF;
    }
}
