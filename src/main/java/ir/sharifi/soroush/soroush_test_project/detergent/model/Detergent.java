package ir.sharifi.soroush.soroush_test_project.detergent.model;

import ir.sharifi.soroush.soroush_test_project.base.model.Product;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "detergent")
@DiscriminatorValue(value= "D")
@Data
@NoArgsConstructor
public class Detergent extends Product {

    @Override
    public void setType(ProductType type) {
        super.setType(ProductType.DETERGENT);
    }
    @Override
    public ProductType getType() {
        return ProductType.DETERGENT;
    }
}
