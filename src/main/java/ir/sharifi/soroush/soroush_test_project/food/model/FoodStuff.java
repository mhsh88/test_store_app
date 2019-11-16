package ir.sharifi.soroush.soroush_test_project.food.model;

import ir.sharifi.soroush.soroush_test_project.base.model.Product;
import ir.sharifi.soroush.soroush_test_project.base.model.ProductType;
import lombok.Data;

import javax.persistence.*;

@Entity
@DiscriminatorValue(value= ProductType.Values.FOODSTUFF)
public @Data
class FoodStuff extends Product {
}
