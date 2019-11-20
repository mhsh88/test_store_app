package ir.sharifi.soroush.soroush_test_project.user.model;

import ir.sharifi.soroush.soroush_test_project.base.model.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "app_user")
@EqualsAndHashCode(callSuper = true)
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public  class AppUser extends BaseEntity<Integer> {

    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "personnel_number")
    private int personnelNumber;
    @Column(name = "user_name",unique = true)
    private String userName;
    @Column(name = "password")
    private String password;
}
