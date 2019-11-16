package ir.sharifi.soroush.soroush_test_project.user.model;

import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "user")
@EqualsAndHashCode(callSuper = true)
@Builder
public @Data class AppUser extends BaseEntity<Integer> {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "personnel_number")
    private int personnelNumber;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "password")
    private String password;
}
