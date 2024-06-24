package org.velog.db.role;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;
import org.velog.db.user.UserEntity;

import java.time.LocalDateTime;

@Entity(name = "user_roles")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserRoleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    @JsonIgnore
    private RoleEntity roleEntity;

    public void setRegistrationDate(){
        super.setRegistrationDate(LocalDateTime.now());
    }

    public void editUserRole(RoleEntity roleEntity){
        //this.userEntity = userEntity;
        this.roleEntity = roleEntity;
    }
}

