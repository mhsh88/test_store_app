package ir.sharifi.soroush.soroush_test_project.base.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;


@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="type",
        discriminatorType=DiscriminatorType.STRING
)
@EqualsAndHashCode(callSuper = true)
public @Data
class Product extends BaseEntity<Long> {
    private String name;
    private String producer;
    private long isbn;
    private LocalDateTime bringInDate;
    private LocalDateTime bringOutDate;
    private LocalDate productionDate;
    private LocalDate expirationDate;
    private double quantity;
    @Column(name="unit")
    @Enumerated(EnumType.STRING)
    private Unit unit;
    @Column(name="type")
    @Convert(converter = ProductTypeConverter.class)
    private ProductType type;
}
