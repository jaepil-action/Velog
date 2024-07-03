package org.velog.db.user;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.velog.db.BaseEntity;
import org.velog.db.blog.BlogEntity;
import org.velog.db.follow.FollowEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @OneToOne(mappedBy = "userEntity", fetch = FetchType.LAZY)
    private BlogEntity blogEntity;

    @Column(length = 50, nullable = false, unique = true)
    private String loginId;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 100, nullable = false)
    private String name;

    @Column(length = 100, nullable = false)
    private String email;

    @Embedded
    private ProfileImage profileImage;

    // TODO 팔로어 삭제시
    /*@OneToMany(mappedBy = "follower", cascade = CascadeType.REMOVE)
    private List<FollowEntity> followEntityList = new ArrayList<>();*/

    public void changeEmail(String email){
        this.email = email;
    }

    public void addBlogEntity(BlogEntity blogEntity){
        this.blogEntity = blogEntity;
    }
    public void setRegistrationDate(){
        super.setRegistrationDate(LocalDateTime.now());
    }
}
