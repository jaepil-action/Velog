package org.velog.db.role;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id")
    private RoleEntity roleEntity;

    private LocalDateTime authorizationDate;

    public void setAuthorizationDate(){
        this.authorizationDate = LocalDateTime.now();
    }
    public void setUserRoleDefault(){
        this.roleEntity = new RoleEntity(Admin.ROLE_USER);
    }
}

