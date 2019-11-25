package ir.sharifi.soroush.soroush_test_project.base.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;


@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="type",
        discriminatorType=DiscriminatorType.STRING
)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public @Data
class Product extends BaseEntity<Long> {

    @NotNull
    private String name;
    @NotNull
    private String producer;
    private long isbn;
    @NotNull
    private LocalDateTime bringInDate;
    private LocalDateTime bringOutDate;
    @NotNull
    private LocalDate productionDate;
    @NotNull
    private LocalDate expirationDate;
    @NotNull
    private double quantity;
    @NotNull
    @Column(name="unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;
    @Column(name="type")
    @Convert(converter = ProductTypeConverter.class)
    private ProductType type;

    @AssertTrue(message = "product.expire.date.is.before.bringIn.date")
    private boolean isNotExpired() {
        return bringInDate.isBefore(expirationDate.atStartOfDay());
    }
    @AssertTrue(message = "product.bringIn.date.is.after.bringOut.date")
    private boolean isBringInBeforeBringOut(){
        if(bringOutDate!=null){
            return bringInDate.isBefore(bringOutDate);
        }
        return true;
    }
    @AssertTrue(message = "product.production.date.is.after.bringIn.date")
    private boolean isProductionBeforeBringIn(){
        return bringInDate.isAfter(productionDate.atStartOfDay());
    }
    @AssertTrue(message = "product.production.date.is.after.expiration.date")
    private boolean isProductionBeforeExpiration(){
        return productionDate.isBefore(expirationDate);
    }
}
