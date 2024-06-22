package org.velog.db.role;

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

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    private LocalDateTime authorizationDate;

    public void setAuthorizationDate(){
        this.authorizationDate = LocalDateTime.now();
    }
    public void editUserRole(RoleEntity roleEntity){
        //this.userEntity = userEntity;
        this.roleEntity = roleEntity;
    }
}

