package ir.sharifi.soroush.soroush_test_project.base.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Data
public
class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ID id;
}
