package ir.sharifi.soroush.soroush_test_project.food.model;

import ir.sharifi.soroush.soroush_test_project.base.model.Product;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "food")
@DiscriminatorValue(value= "F")
@Data
@NoArgsConstructor
public
class FoodStuff extends Product {
    @Override
    public void setType(ProductType type) {
        super.setType(ProductType.FOODSTUFF);
    }

    @Override
    public ProductType getType() {
        return ProductType.FOODSTUFF;
    }
}
