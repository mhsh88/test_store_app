package ir.sharifi.soroush.soroush_test_project.detergent.model;

import ir.sharifi.soroush.soroush_test_project.base.model.Product;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import lombok.Data;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value= ProductType.Values.DETERGENT)
public @Data
class Detergent extends Product {
}
